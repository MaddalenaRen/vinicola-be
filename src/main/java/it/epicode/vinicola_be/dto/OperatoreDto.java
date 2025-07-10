package it.epicode.vinicola_be.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OperatoreDto {
    @NotEmpty(message = "il nome è obbligatorio")
    private String nome;

    @NotEmpty(message = "il cognome è obbligatorio")
    private String cognome;

    @NotEmpty(message = "il reparto è obbligatorio")
    private String reparto;

    private String numeroTelefono;

    private Long utenteId;
}
