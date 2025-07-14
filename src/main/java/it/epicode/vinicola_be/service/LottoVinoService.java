package it.epicode.vinicola_be.service;

import it.epicode.vinicola_be.dto.LottoVinoDto;
import it.epicode.vinicola_be.exception.NotFoundException;
import it.epicode.vinicola_be.model.*;
import it.epicode.vinicola_be.repository.EtichettaRepository;
import it.epicode.vinicola_be.repository.FaseProduzioneRepository;
import it.epicode.vinicola_be.repository.LottoVinoRepository;
import it.epicode.vinicola_be.repository.UvaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LottoVinoService {
    @Autowired
    private LottoVinoRepository lottoVinoRepository;

    @Autowired
    private EtichettaRepository etichettaRepository;

    @Autowired
    private UvaRepository uvaRepository;

    @Autowired
    private FaseProduzioneRepository faseProduzioneRepository;

    public LottoVino saveLottoVino(LottoVinoDto lottoVinoDto) {
        LottoVino lotto = new LottoVino();

        lotto.setNome(lottoVinoDto.getNome());
        lotto.setQuantita(lottoVinoDto.getQuantita());
        lotto.setAnnata(lottoVinoDto.getAnnata());
        lotto.setVarietaUva(lottoVinoDto.getVarietaUva());


        Uva uva = uvaRepository.findById(lottoVinoDto.getUvaId())
                .orElseThrow(() -> new NotFoundException("Uva non trovata"));
        lotto.setUva(uva);


        if (lottoVinoDto.getEtichettaId() != null) {
            Etichetta etichetta = etichettaRepository.findById(lottoVinoDto.getEtichettaId())
                    .orElseThrow(() -> new NotFoundException("Etichetta non trovata"));
            lotto.setEtichetta(etichetta);
        }


        List<FaseProduzione> fasi = faseProduzioneRepository.findAllById(lottoVinoDto.getFasiProduzioneIds());
        lotto.setFasiProduzione(fasi);

        return lottoVinoRepository.save(lotto);
    }


    public Page<LottoVino> getAllLotti(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return lottoVinoRepository.findAll(pageable);
    }

    public  LottoVino getLotto(long idLotto) throws NotFoundException {
        return lottoVinoRepository.findById(idLotto)
                .orElseThrow(() -> new NotFoundException("Lotto con id: " + idLotto + " non trovato"));
    }

    public LottoVino updateLotto(long idlotto, LottoVinoDto lottoVinoDto) {
        LottoVino lottoDaAggiornare = getLotto(idlotto);

        lottoDaAggiornare.setQuantita(lottoVinoDto.getQuantita());
        lottoDaAggiornare.setNome(lottoVinoDto.getNome());
        lottoDaAggiornare.setAnnata(lottoVinoDto.getAnnata());
        lottoDaAggiornare.setVarietaUva(lottoVinoDto.getVarietaUva());


        Uva uva = uvaRepository.findById(lottoVinoDto.getUvaId())
                .orElseThrow(() -> new NotFoundException("Uva non trovata"));
        lottoDaAggiornare.setUva(uva);


        if (lottoVinoDto.getEtichettaId() != null) {
            Etichetta etichetta = etichettaRepository.findById(lottoVinoDto.getEtichettaId())
                    .orElseThrow(() -> new NotFoundException("Etichetta non trovata"));
            lottoDaAggiornare.setEtichetta(etichetta);
        }


        List<FaseProduzione> fasi = faseProduzioneRepository.findAllById(lottoVinoDto.getFasiProduzioneIds());
        lottoDaAggiornare.setFasiProduzione(fasi);

        return lottoVinoRepository.save(lottoDaAggiornare);
    }

    public void deleteLotto(long idLotto) throws NotFoundException {
        LottoVino lottoDaRimuovere = getLotto(idLotto);
        lottoVinoRepository.delete(lottoDaRimuovere);
    }
}

