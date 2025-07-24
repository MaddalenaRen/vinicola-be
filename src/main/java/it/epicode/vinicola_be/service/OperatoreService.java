package it.epicode.vinicola_be.service;

import it.epicode.vinicola_be.dto.OperatoreDto;
import it.epicode.vinicola_be.exception.NotFoundException;
import it.epicode.vinicola_be.model.FaseProduzione;
import it.epicode.vinicola_be.model.Operatore;
import it.epicode.vinicola_be.model.Ordine;
import it.epicode.vinicola_be.repository.OperatoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OperatoreService {

    @Autowired
    private OperatoreRepository operatoreRepository;

    public Operatore saveOperatore(OperatoreDto operatoreDto){
        Operatore operatore = new Operatore();
        operatore.setNome(operatoreDto.getNome());
        operatore.setCognome(operatoreDto.getCognome());
        operatore.setReparto(operatoreDto.getReparto());
        operatore.setNumeroTelefono(operatoreDto.getNumeroTelefono());
        operatore.setFasiGestite(new ArrayList<>());
        operatore.setOrdiniGestiti(new ArrayList<>());

        return operatoreRepository.save(operatore);
    }

    public Page<Operatore> searchByNome(String nome, int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return operatoreRepository.findByNomeContainingIgnoreCase(nome, pageable);
    }

    public Page<Operatore> getAllOperatori(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return operatoreRepository.findAll(pageable);
    }

    public Operatore getOperatore(long idOperatore) throws NotFoundException {
        return operatoreRepository.findById(idOperatore)
                .orElseThrow(() -> new NotFoundException("Operatore con id: " + idOperatore + " non trovato")); // CORRETTO MESSAGGIO
    }

    public Operatore updateOperatore(long idOperatore, OperatoreDto operatoreDto) throws NotFoundException {
        Operatore operatoreDaAggiornare = getOperatore(idOperatore);
        operatoreDaAggiornare.setNome(operatoreDto.getNome());
        operatoreDaAggiornare.setCognome(operatoreDto.getCognome());
        operatoreDaAggiornare.setReparto(operatoreDto.getReparto());
        operatoreDaAggiornare.setNumeroTelefono(operatoreDto.getNumeroTelefono());

        return operatoreRepository.save(operatoreDaAggiornare);
    }

    public List<Operatore> findByReparto(String reparto) {
        return operatoreRepository.findByReparto(reparto);
    }

    public List<FaseProduzione> getFasiProduzioneByOperatore(long idOperatore) throws NotFoundException {
        return getOperatore(idOperatore).getFasiGestite();
    }

    public List<Ordine> getOrdiniGestitiByOperatore(long idOperatore) throws NotFoundException {
        return getOperatore(idOperatore).getOrdiniGestiti();
    }

    public Operatore addFaseProduzione(long idOperatore, FaseProduzione fase) throws NotFoundException {
        Operatore operatore = getOperatore(idOperatore);
        operatore.getFasiGestite().add(fase);
        return operatoreRepository.save(operatore);
    }

    public Operatore addOrdineGestito(long idOperatore, Ordine ordine) throws NotFoundException {
        Operatore operatore = getOperatore(idOperatore);
        operatore.getOrdiniGestiti().add(ordine);
        return operatoreRepository.save(operatore);
    }
    public Operatore removeFaseProduzione(long idOperatore, FaseProduzione fase) throws NotFoundException {
        Operatore operatore = getOperatore(idOperatore);
        operatore.getFasiGestite().remove(fase);
        return  operatoreRepository.save(operatore);
    }

    public Operatore removeOrdine(long idOperatore , Ordine ordine) throws NotFoundException {
        Operatore operatore = getOperatore(idOperatore);
        operatore.getOrdiniGestiti().remove(ordine);
        return operatoreRepository.save(operatore);
    }

    public Operatore removeAllFasiProduzione(long idOperatore) throws NotFoundException {
        Operatore operatore = getOperatore(idOperatore);
        operatore.getFasiGestite().clear();
        return operatoreRepository.save(operatore);

    }

    public Operatore removeAllOrdiniGestiti(long idOperatore) throws NotFoundException {
        Operatore operatore = getOperatore(idOperatore);
        operatore.getOrdiniGestiti().clear();
        return operatoreRepository.save(operatore);
    }


    public void deleteOperatore(long idOperatore) throws NotFoundException {
        Operatore operatore = getOperatore(idOperatore);
        operatoreRepository.delete(operatore);
    }


}
