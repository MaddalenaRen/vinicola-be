package it.epicode.vinicola_be.service;

import it.epicode.vinicola_be.dto.FaseProduzioneDto;
import it.epicode.vinicola_be.enumeration.TipoFase;
import it.epicode.vinicola_be.exception.NotFoundException;
import it.epicode.vinicola_be.model.FaseProduzione;
import it.epicode.vinicola_be.model.LottoVino;
import it.epicode.vinicola_be.model.Operatore;
import it.epicode.vinicola_be.repository.FaseProduzioneRepository;
import it.epicode.vinicola_be.repository.LottoVinoRepository;
import it.epicode.vinicola_be.repository.OperatoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FaseProduzioneService {

    @Autowired
    private FaseProduzioneRepository faseProduzioneRepository;

    @Autowired
    private LottoVinoRepository lottoVinoRepository;

    @Autowired
    private OperatoreRepository operatoreRepository;

    public FaseProduzione saveFaseProduzione(FaseProduzioneDto faseProduzioneDto,
                                             Long idLotto,Long idOperatore) throws NotFoundException {
        LottoVino lottoVino = lottoVinoRepository.findById(idLotto)
                .orElseThrow(() -> new NotFoundException("Lotto con id: " + idLotto + " trovato"));

        Operatore operatore = operatoreRepository.findById(idOperatore)
                .orElseThrow(() -> new NotFoundException("Operatore con id: " + idOperatore + " non trovato"));

        FaseProduzione fase = new FaseProduzione();
        fase.setTipoFase(faseProduzioneDto.getTipoFase());
        fase.setDataInizio(faseProduzioneDto.getDataInizio());
        fase.setDataFine(faseProduzioneDto.getDataFine());
        fase.setNote(faseProduzioneDto.getNote());
        fase.setLottoVino(lottoVino);
        fase.setOperatore(operatore);

        return faseProduzioneRepository.save(fase);
    }

    public Page<FaseProduzione> getAllFasiProduzione(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return faseProduzioneRepository.findAll(pageable);
    }

    public FaseProduzione getFaseProduzione(long idFase) throws NotFoundException {
        return faseProduzioneRepository.findById(idFase)
                .orElseThrow(() -> new NotFoundException("Fase con id: " + idFase + " non trovato")); // CORRETTO MESSAGGIO
    }

    public FaseProduzione updateFaseProduzione(Long idFase, FaseProduzioneDto faseProduzioneDto) throws NotFoundException {
        FaseProduzione fase = getFaseProduzione(idFase);
        fase.setTipoFase(faseProduzioneDto.getTipoFase());
        fase.setDataInizio(faseProduzioneDto.getDataInizio());
        fase.setDataFine(faseProduzioneDto.getDataFine());
        fase.setNote(faseProduzioneDto.getNote());

        return faseProduzioneRepository.save(fase);
    }

    public Page<FaseProduzione> findByOperatore(Long idOperatore, int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return faseProduzioneRepository.findByOperatore_Id(idOperatore, pageable);
    }

    public Page<FaseProduzione> findByLotto(Long idLotto, int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return faseProduzioneRepository.findByLottoVino_Id(idLotto, pageable);
    }



    public void deleteFaseProduzione(Long idFase) throws NotFoundException {
        FaseProduzione fase = getFaseProduzione(idFase);
        faseProduzioneRepository.delete(fase);
    }

    public List<Map<String, String>> getAllTipoFasi(){
        return Arrays.stream(TipoFase.values()).map(stato -> {
            Map<String, String> mappa = new HashMap<>();
            mappa.put("id", stato.key);
            mappa.put("descrizione", stato.descrizione);

            return mappa;
        }).collect(Collectors.toList());
    }

}

