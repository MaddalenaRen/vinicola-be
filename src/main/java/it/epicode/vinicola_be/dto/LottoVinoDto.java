package it.epicode.vinicola_be.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class LottoVinoDto {
    @Min(value = 100, message = "La quantità deve essere almeno 100")
    private int quantita;

    @NotEmpty(message = "Il nome è obbligatorio")
    private String nome;

    @NotNull(message = "L'annata è obbligatoria")
    private int annata;

    @NotEmpty(message = "La varietà dell'uva è obbligatoria")
    private String varietaUva;

    @NotNull(message = "L'ID dell'uva è obbligatorio")
    private Long uvaId;

    private List<Long> fasiProduzioneIds;

    @NotNull(message = "L'ID dell'etichetta è obbligatorio")
    private Long etichettaId;
}
