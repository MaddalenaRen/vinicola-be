package it.epicode.vinicola_be.dto;

import it.epicode.vinicola_be.model.Etichetta;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class LottoVinoDto {

    private Long id;

    @Min(value = 100, message = "La quantità deve essere almeno 100")
    private int quantita;

    @NotEmpty(message = "Il nome è obbligatorio")
    private String nome;

    @NotNull(message = "L'annata è obbligatoria")
    private int annata;

    @NotEmpty(message = "La varietà dell'uva è obbligatoria")
    private String varietaUva;

    private String fasiProduzioneIds;

    @NotNull(message = "Nome etichetta")
    private Etichetta etichetta;
}
