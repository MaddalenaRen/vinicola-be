package it.epicode.vinicola_be.service;

import it.epicode.vinicola_be.dto.OrdineDto;
import it.epicode.vinicola_be.enumeration.StatoOrdine;
import it.epicode.vinicola_be.exception.NotFoundException;
import it.epicode.vinicola_be.model.Cliente;
import it.epicode.vinicola_be.model.Etichetta;
import it.epicode.vinicola_be.model.Operatore;
import it.epicode.vinicola_be.model.Ordine;
import it.epicode.vinicola_be.repository.ClienteRepository;
import it.epicode.vinicola_be.repository.EtichettaRepository;
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

    @Autowired
    private EtichettaRepository etichettaRepository;


    public Ordine saveOrdine(OrdineDto ordineDto) throws NotFoundException {
        Ordine ordine = new Ordine();
        ordine.setQuantita(ordineDto.getQuantita());
        ordine.setDataOrdine(ordineDto.getDataOrdine());
        ordine.setDataConsegna(ordineDto.getDataConsegna());
        ordine.setStato(StatoOrdine.IN_ATTESA);
        if (ordineDto.getCliente() == null) {
            throw new NotFoundException("ClienteId non può essere nullo");
        }
        Cliente cliente = clienteRepository.findById(ordineDto.getCliente().getId())
                .orElseThrow(() -> new NotFoundException("Cliente non trovato : " + ordineDto.getCliente()));
        ordine.setCliente(cliente);
        if (ordineDto.getOperatore() != null) {
            Operatore operatore = operatoreRepository.findById(ordineDto.getOperatore().getId())
                    .orElseThrow(() -> new NotFoundException("Operatore non trovato : " + ordineDto.getOperatore()));
            ordine.setOperatore(operatore);
        }

        Etichetta etichetta = etichettaRepository.findById(ordineDto.getEtichetta().getId()).orElse(null);
        ordine.setEtichetta(etichetta);
        System.out.println(etichetta != null ? etichetta.getId() : null);

        etichetta.getOrdini().add(ordine);

        ordineRepository.save(ordine);

        etichettaRepository.save(etichetta);


        return ordine;
    }

    public Page<Ordine> getAllOrdini(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return ordineRepository.findAll(pageable);
    }

    public Page<Ordine> searchByCliente(String cliente, int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return ordineRepository.findByClienteNomeOrCognome(cliente, pageable);
    }

    public Ordine getOrdine(long idOrdine) throws NotFoundException{
        return ordineRepository.findById(idOrdine).orElseThrow(() -> new NotFoundException("Ordine con id:" + idOrdine + " non trovato"));
    }

    public Ordine updateOrdine(OrdineDto ordineDto,long idOrdine) throws NotFoundException {
        Ordine ordineDaAggiornare = getOrdine(idOrdine);
        ordineDaAggiornare.setQuantita(ordineDto.getQuantita());
        ordineDaAggiornare.setDataOrdine(ordineDto.getDataOrdine());
        ordineDaAggiornare.setDataConsegna(ordineDto.getDataConsegna());
        ordineDaAggiornare.setStato(StatoOrdine.valueOf(ordineDto.getStato()));
        if (ordineDto.getCliente() == null) {
            throw new NotFoundException("ClienteId non può essere nullo");
        }
        Cliente cliente = clienteRepository.findById(ordineDto.getCliente().getId())
                .orElseThrow(() -> new NotFoundException("Cliente non trovato con id: " + ordineDto.getCliente()));
        ordineDaAggiornare.setCliente(cliente);
        if (ordineDto.getOperatore() != null) {
            Operatore operatore = operatoreRepository.findById(ordineDto.getOperatore().getId())
                    .orElseThrow(() -> new NotFoundException("Operatore non trovato con id: " + ordineDto.getOperatore()));
            ordineDaAggiornare.setOperatore(operatore);
        }

        ordineDaAggiornare.setEtichetta(etichettaRepository.findById(ordineDto.getEtichetta().getId()).orElse(null));
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
