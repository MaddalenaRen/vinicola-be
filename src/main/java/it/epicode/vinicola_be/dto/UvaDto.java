package it.epicode.vinicola_be.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UvaDto {

    @NotEmpty(message = "Il tipo di uva è obbligatorio")
    private String tipoUva;

    @NotEmpty(message = "Il vigneto di provenienza è obbligatorio")
    private String vignetoDiProveninza;

    @Min(value = 0, message = "Il grado zucchero deve essere positivo")
    private int gradoZucchero;

    @Min(value = 0, message = "La quantità raccolta deve essere positiva")
    private int quantitaRaccolta;

    @NotEmpty(message = "L'id della cantina è obbligatorio")
    private Integer cantinaId;
}