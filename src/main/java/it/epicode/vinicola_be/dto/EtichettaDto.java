package it.epicode.vinicola_be.dto;

import it.epicode.vinicola_be.enumeration.TipologiaVino;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class EtichettaDto {

    @NotNull(message = "La tipologia di vino è obbligatoria")
    private TipologiaVino tipologiaVino;

    @NotNull(message = "la gradazione alcolica è obbligatoria")
    private double gradazioneAlcolica;

    @NotNull(message = "La data di imbottigliamento è obbligatoria")
    private LocalDate dataImbottigliamento;

    @NotNull(message = "L'ID della cantina è obbligatorio")
    private Long cantinaId;

    private List<Long> lottiId;

    private List<Long> ordineEtichetteId;


}
