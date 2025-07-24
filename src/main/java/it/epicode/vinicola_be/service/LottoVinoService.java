package it.epicode.vinicola_be.service;

import it.epicode.vinicola_be.dto.LottoVinoDto;
import it.epicode.vinicola_be.enumeration.TipoFase;
import it.epicode.vinicola_be.exception.NotFoundException;
import it.epicode.vinicola_be.model.*;
import it.epicode.vinicola_be.repository.EtichettaRepository;
import it.epicode.vinicola_be.repository.FaseProduzioneRepository;
import it.epicode.vinicola_be.repository.LottoVinoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;


@Service
public class LottoVinoService {

    @Autowired
    private LottoVinoRepository lottoVinoRepository;

    @Autowired
    private EtichettaRepository etichettaRepository;

    @Autowired
    private FaseProduzioneRepository faseProduzioneRepository;

    public LottoVino saveLottoVino(LottoVinoDto lottoVinoDto) {
        LottoVino lotto = new LottoVino();

        lotto.setNome(lottoVinoDto.getNome());
        lotto.setQuantita(lottoVinoDto.getQuantita());
        lotto.setAnnata(lottoVinoDto.getAnnata());
        lotto.setVarietaUva(lottoVinoDto.getVarietaUva());

        if (lottoVinoDto.getEtichetta() != null) {
            Etichetta etichetta = etichettaRepository.findByNomeEtichetta(lottoVinoDto.getEtichetta().getNomeEtichetta())
                    .orElseThrow(() -> new NotFoundException("Etichetta non trovata"));
            lotto.setEtichetta(etichetta);
        }

        lotto.setFaseProduzione(TipoFase.getKeyByDescrizione(lottoVinoDto.getFasiProduzioneIds())                );

        return lottoVinoRepository.save(lotto);
    }

    public Page<LottoVino> searchByNome(String nome, int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return lottoVinoRepository.findByNomeContainingIgnoreCase(nome, pageable);
    }


    public Page<LottoVino> getAllLotti(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return lottoVinoRepository.findAll(pageable);
    }

    public Page<LottoVinoDto> convertiLottoVino(Page<LottoVino> lottiVino){

        return lottiVino.map(lv -> {
            LottoVinoDto lottoVinoDto = new LottoVinoDto();
            lottoVinoDto.setId(lv.getId());
            lottoVinoDto.setNome(lv.getNome());
            lottoVinoDto.setAnnata(lv.getAnnata());
            lottoVinoDto.setQuantita(lv.getQuantita());
            lottoVinoDto.setEtichetta(lv.getEtichetta());
            lottoVinoDto.setFasiProduzioneIds(TipoFase.getDescrizioneByKey(lv.getFaseProduzione()));
            lottoVinoDto.setVarietaUva(lv.getVarietaUva());
            return lottoVinoDto;
        });

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

        if (lottoVinoDto.getEtichetta() != null) {
            Etichetta etichetta = etichettaRepository.findByNomeEtichetta(lottoVinoDto.getEtichetta().getNomeEtichetta())
                    .orElseThrow(() -> new NotFoundException("Etichetta non trovata"));
            lottoDaAggiornare.setEtichetta(etichetta);
        }


        lottoDaAggiornare.setFaseProduzione(TipoFase.getKeyByDescrizione(lottoVinoDto.getFasiProduzioneIds()));

        return lottoVinoRepository.save(lottoDaAggiornare);
    }

    public void deleteLotto(long idLotto) throws NotFoundException {
        LottoVino lottoDaRimuovere = getLotto(idLotto);
        lottoVinoRepository.delete(lottoDaRimuovere);
    }
}

