package it.epicode.vinicola_be.service;

import it.epicode.vinicola_be.dto.BottigliaVinoDto;
import it.epicode.vinicola_be.exception.NotFoundException;
import it.epicode.vinicola_be.model.*;
import it.epicode.vinicola_be.repository.BottigliaVinoRepository;
import it.epicode.vinicola_be.repository.EtichettaRepository;
import it.epicode.vinicola_be.repository.LottoVinoRepository;
import it.epicode.vinicola_be.repository.OrdineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class BottigliaVinoService {
    @Autowired
    private BottigliaVinoRepository bottigliaVinoRepository;

    @Autowired
    private EtichettaRepository etichettaRepository;

    @Autowired
    private LottoVinoRepository lottoVinoRepository;

    @Autowired
    private OrdineRepository ordineRepository;

    public BottigliaVino saveBottigliaVino(BottigliaVinoDto bottigliaVinoDto,
                                           Long idEtichetta, Long idLotto, Long idOrdine) throws NotFoundException {
        BottigliaVino bottigliaVino = new BottigliaVino();

        Etichetta etichetta = etichettaRepository.findById(idEtichetta)
                .orElseThrow(() -> new NotFoundException("Etichetta con id: " + idEtichetta + "non trovato"));


        LottoVino lottoVino = lottoVinoRepository.findById(idLotto)
                .orElseThrow(() -> new NotFoundException("Lotto con id: " + idLotto + "non trovato"));

        Ordine ordine = ordineRepository.findById(idOrdine)
                .orElseThrow(() -> new NotFoundException("Ordine con id: " + idOrdine + "non trovato"));

        bottigliaVino.setEtichetta(etichetta);
        bottigliaVino.setLotto(lottoVino);
        bottigliaVino.setOrdine(ordine);

        bottigliaVino.setImmagineUrl(bottigliaVinoDto.getImmagineUrl());

        return bottigliaVinoRepository.save(bottigliaVino);
    }

    public Page<BottigliaVino> getAllBottiglieVino(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return bottigliaVinoRepository.findAll(pageable);
    }

    public BottigliaVino getBottigliaVino(long idBottigliaVino) throws NotFoundException {
        return bottigliaVinoRepository.findById(idBottigliaVino)
                .orElseThrow(() -> new NotFoundException("Bottiglia con id: " + idBottigliaVino + " non trovato")); // CORRETTO MESSAGGIO
    }


}
