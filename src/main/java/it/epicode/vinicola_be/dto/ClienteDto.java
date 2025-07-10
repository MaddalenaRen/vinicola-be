package it.epicode.vinicola_be.dto;

import it.epicode.vinicola_be.enumeration.TipoCliente;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ClienteDto {
    @NotEmpty(message = "Il nome è obbligatorio")
    private String nome;

    @NotEmpty(message = "Il cognome è obbligatorio")
    private String cognome;
    @NotEmpty(message = "L'email è obbligatoria")
    @Email(message = "Email non valida")
    private String email;
    private String partitaIva;
    @NotNull(message = "Il tipo cliente è obbligatorio")
    private TipoCliente tipoCliente;
}
