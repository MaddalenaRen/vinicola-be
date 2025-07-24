package it.epicode.vinicola_be.dto;

import lombok.Data;

import java.util.List;

@Data
public class FaseLottiDto {

    private Double percentualeLotti;

    private String nomeLotto;

    private List<String> lotti;

    private double percentualeBottiglie;

}
