package it.epicode.vinicola_be.dto;

import it.epicode.vinicola_be.enumeration.TipoCliente;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @Size(min = 16, max = 16, message = "Il codice fiscale deve contenere 16 caratteri")
    private String codiceFiscale;

    @Size(min = 11, max = 11, message = "La partita IVA deve contenere 11 cifre")
    private String partitaIva;

    @NotNull(message = "Il tipo cliente è obbligatorio")
    private TipoCliente tipoCliente;
}
