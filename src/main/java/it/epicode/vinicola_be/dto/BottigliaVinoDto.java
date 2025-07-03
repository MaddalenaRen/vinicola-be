package it.epicode.vinicola_be.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BottigliaVinoDto {

    private String immagineUrl;

    @NotNull(message = "L'etichetta è obbligatoria")
    private Long etichettaId;

    @NotNull(message = "Il lotto è obbligatorio")
    private Long lottoId;

    private Long ordineId;


}
