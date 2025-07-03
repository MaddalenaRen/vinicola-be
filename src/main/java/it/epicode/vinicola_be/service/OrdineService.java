package it.epicode.vinicola_be.service;

import it.epicode.vinicola_be.dto.OrdineDto;
import it.epicode.vinicola_be.enumeration.StatoOrdine;
import it.epicode.vinicola_be.exception.NotFoundException;
import it.epicode.vinicola_be.model.Cliente;
import it.epicode.vinicola_be.model.Operatore;
import it.epicode.vinicola_be.model.Ordine;
import it.epicode.vinicola_be.repository.ClienteRepository;
import it.epicode.vinicola_be.repository.OperatoreRepository;
import it.epicode.vinicola_be.repository.OrdineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class OrdineService {

    @Autowired
    private OrdineRepository ordineRepository;
    @Autowired
    private OperatoreRepository operatoreRepository;
    @Autowired
    private ClienteRepository clienteRepository;

public Ordine saveOrdine(OrdineDto ordineDto) throws NotFoundException {
    Ordine ordine = new Ordine();
    ordine.setQuantita(ordineDto.getQuantita());
    ordine.setDataOrdine(ordineDto.getDataOrdine());
    ordine.setDataConsegna(ordineDto.getDataConsegna());
    ordine.setStato(StatoOrdine.IN_ATTESA);
    if (ordineDto.getClienteId() == null) {
        throw new NotFoundException("ClienteId non può essere nullo");
    }
    Cliente cliente = clienteRepository.findById(ordineDto.getClienteId())
            .orElseThrow(() -> new NotFoundException("Cliente non trovato con id: " + ordineDto.getClienteId()));
    ordine.setCliente(cliente);
    if (ordineDto.getOperatoreId() != null) {
        Operatore operatore = operatoreRepository.findById(ordineDto.getOperatoreId())
                .orElseThrow(() -> new NotFoundException("Operatore non trovato con id: " + ordineDto.getOperatoreId()));
        ordine.setOperatore(operatore);
    }
    return ordineRepository.save(ordine);
}

public Page<Ordine> getAllOrdini(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return ordineRepository.findAll(pageable);
}

public Ordine getOrdine(long idOrdine) throws NotFoundException{
    return ordineRepository.findById(idOrdine).orElseThrow(() -> new NotFoundException("Ordine con id:" + idOrdine + " non trovato"));
}

public Ordine updateOrdine(OrdineDto ordineDto,long idOrdine) throws NotFoundException {
        Ordine ordineDaAggiornare = getOrdine(idOrdine);
        ordineDaAggiornare.setQuantita(ordineDto.getQuantita());
        ordineDaAggiornare.setDataOrdine(ordineDto.getDataOrdine());
        ordineDaAggiornare.setDataConsegna(ordineDto.getDataConsegna());
        ordineDaAggiornare.setStato(StatoOrdine.IN_ATTESA);
        if (ordineDto.getClienteId() == null) {
            throw new NotFoundException("ClienteId non può essere nullo");
        }
        Cliente cliente = clienteRepository.findById(ordineDto.getClienteId())
                .orElseThrow(() -> new NotFoundException("Cliente non trovato con id: " + ordineDto.getClienteId()));
        ordineDaAggiornare.setCliente(cliente);
        if (ordineDto.getOperatoreId() != null) {
            Operatore operatore = operatoreRepository.findById(ordineDto.getOperatoreId())
                    .orElseThrow(() -> new NotFoundException("Operatore non trovato con id: " + ordineDto.getOperatoreId()));
            ordineDaAggiornare.setOperatore(operatore);
        }
        return ordineRepository.save(ordineDaAggiornare);
}

public Ordine aggiornaStatoOrdine(long idOrdine, StatoOrdine nuovoStato) throws NotFoundException {
    Ordine ordine = getOrdine(idOrdine);
    ordine.setStato(nuovoStato);
        return ordineRepository.save(ordine);
}

public Ordine aggiornaOperatore(long idOrdine, long idOperatore) throws NotFoundException {
    Ordine ordine = getOrdine(idOrdine);
    Operatore operatore =  operatoreRepository.findById(idOperatore)
            .orElseThrow(() ->new NotFoundException("Operatore non trovato con id: " + idOperatore));
    ordine.setOperatore(operatore);
    return ordineRepository.save(ordine);
}

public Ordine aggiornaDataConsegna(long idOrdine, LocalDate nuovaDataConsegna) throws NotFoundException {
    Ordine ordine = getOrdine(idOrdine);
    ordine.setDataConsegna(nuovaDataConsegna);
    return ordineRepository.save(ordine);
}

public void deleteOrdine(long idOrdine) throws NotFoundException {
    Ordine ordineDaRimuovere = getOrdine(idOrdine);
    ordineRepository.delete(ordineDaRimuovere);
}



}
