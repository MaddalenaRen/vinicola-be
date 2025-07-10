package it.epicode.vinicola_be.service;


import it.epicode.vinicola_be.dto.EtichettaDto;
import it.epicode.vinicola_be.exception.NotFoundException;
import it.epicode.vinicola_be.model.Cantina;
import it.epicode.vinicola_be.model.Etichetta;
import it.epicode.vinicola_be.model.LottoVino;
import it.epicode.vinicola_be.model.Ordine;
import it.epicode.vinicola_be.repository.CantinaRepository;
import it.epicode.vinicola_be.repository.EtichettaRepository;
import it.epicode.vinicola_be.repository.LottoVinoRepository;
import it.epicode.vinicola_be.repository.OrdineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EtichettaService {

    @Autowired
    private EtichettaRepository etichettaRepository;
    @Autowired
    private CantinaRepository cantinaRepository;
    @Autowired
    private LottoVinoRepository lottoVinoRepository;
    @Autowired
    private OrdineRepository ordineRepository;

    public Etichetta saveEtichetta(EtichettaDto etichettaDto) throws NotFoundException {
        Etichetta etichetta = new Etichetta();
        etichetta.setNomeEtichetta(etichettaDto.getNomeEtichetta());
        etichetta.setTipologiaVino(etichettaDto.getTipologiaVino());
        etichetta.setGradazioneAlcolica(etichettaDto.getGradazioneAlcolica());
        etichetta.setDataImbottigliamento(etichettaDto.getDataImbottigliamento());
        List<LottoVino> lotti = lottoVinoRepository.findAllById(etichettaDto.getLottiId());

        if (lotti.size() != etichettaDto.getLottiId().size()) {
            throw new NotFoundException("Uno o più lotti non trovati");
        }
        etichetta.setLotti(lotti);
        Cantina cantina = cantinaRepository.findById(etichettaDto.getCantinaId())
                .orElseThrow(() -> new NotFoundException("Cantina non trovata con ID: " + etichettaDto.getCantinaId()));
        etichetta.setCantina(cantina);


        return etichettaRepository.save(etichetta);
    }

    public void associaEtichettaAOrdine(Long etichettaId, Long ordineId) throws NotFoundException {
        Etichetta etichetta = etichettaRepository.findById(etichettaId)
                .orElseThrow(() -> new NotFoundException("Etichetta non trovata"));
        Ordine ordine = ordineRepository.findById(ordineId)
                .orElseThrow(() -> new NotFoundException("Ordine non trovato"));


        etichetta.addOrdine(ordine);

        etichettaRepository.save(etichetta);
        ordineRepository.save(ordine);
    }


    public Page<Etichetta> getAllEtichette(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return etichettaRepository.findAll(pageable);
    }

    public Etichetta getEtichetta(long idEtichetta) throws NotFoundException {
        return etichettaRepository.findById(idEtichetta)
                .orElseThrow(() -> new NotFoundException("Etichetta con id: " + idEtichetta + " non trovato"));
    }

    public Etichetta updateEtichetta(long idEtichetta, EtichettaDto etichettaDto) throws NotFoundException {
        Etichetta etichettaDaAggiornare = getEtichetta(idEtichetta);
        etichettaDaAggiornare.setNomeEtichetta(etichettaDto.getNomeEtichetta());
        etichettaDaAggiornare.setTipologiaVino(etichettaDto.getTipologiaVino());
        etichettaDaAggiornare.setGradazioneAlcolica(etichettaDto.getGradazioneAlcolica());
        etichettaDaAggiornare.setDataImbottigliamento(etichettaDto.getDataImbottigliamento());
        List<LottoVino> lotti = lottoVinoRepository.findAllById(etichettaDto.getLottiId());
        if (lotti.size() != etichettaDto.getLottiId().size()) {
            throw new NotFoundException("Uno o più lotti non trovati");
        }
        etichettaDaAggiornare.setLotti(lotti);

        Cantina cantina = cantinaRepository.findById(etichettaDto.getCantinaId())
                .orElseThrow(() -> new NotFoundException("Cantina non trovata con ID: " + etichettaDto.getCantinaId()));
        etichettaDaAggiornare.setCantina(cantina);

        return etichettaRepository.save(etichettaDaAggiornare);


    }

    public void deleteEtichetta(long idEtichetta) throws NotFoundException {
        Etichetta etichettaDaRimuovere = getEtichetta(idEtichetta);
        etichettaRepository.delete(etichettaDaRimuovere);
    }

    public void rimuoviEtichettaDaOrdine(Long etichettaId, Long ordineId) throws NotFoundException {
        Etichetta etichetta = etichettaRepository.findById(etichettaId)
                .orElseThrow(() -> new NotFoundException("Etichetta non trovata"));
        Ordine ordine = ordineRepository.findById(ordineId)
                .orElseThrow(() -> new NotFoundException("Ordine non trovato"));

        etichetta.removeOrdine(ordine);

        etichettaRepository.save(etichetta);
        ordineRepository.save(ordine);
    }
}
