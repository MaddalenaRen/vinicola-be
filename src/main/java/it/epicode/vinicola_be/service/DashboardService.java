package it.epicode.vinicola_be.service;

import it.epicode.vinicola_be.dto.FaseLottiDto;
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

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    @Autowired
    private LottoVinoRepository lottoVinoRepository;

    public List<FaseLottiDto> getFaseLotti() {
        List<LottoVino> lottiVino = lottoVinoRepository.findAll();
        List<FaseLottiDto> faseLottiDtos = new ArrayList<>();

        int totaleBottiglie = lottiVino.stream()
                .mapToInt(LottoVino::getQuantita)
                .sum();

        for (TipoFase value : TipoFase.values()) {
            FaseLottiDto faseLottiDto = new FaseLottiDto();
            faseLottiDto.setNomeLotto(value.descrizione);


            List<LottoVino> lottiInFase = lottiVino.stream()
                    .filter(lv -> lv.getFaseProduzione().equalsIgnoreCase(value.key))
                    .toList();

            faseLottiDto.setLotti(
                    lottiInFase.stream()
                            .map(LottoVino::getNome)
                            .toList()
            );


            faseLottiDto.setPercentualeLotti(
                    (double) faseLottiDto.getLotti().size() / lottiVino.size() * 100
            );


            int bottiglieInFase = lottiInFase.stream()
                    .mapToInt(LottoVino::getQuantita)
                    .sum();

            double percentualeBottiglie = totaleBottiglie > 0
                    ? (double) bottiglieInFase / totaleBottiglie * 100
                    : 0;

            faseLottiDto.setPercentualeBottiglie(percentualeBottiglie);

            faseLottiDtos.add(faseLottiDto);
        }

        return faseLottiDtos;
    }
}


