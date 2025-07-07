package it.epicode.vinicola_be.dto;

import it.epicode.vinicola_be.enumeration.Ruolo;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UtenteDto {
    @NotEmpty(message="il nome non può essere vuoto")
    private String nome;
    @Email(message = "Inserisci un indirizzo email valido")
    @NotEmpty(message="email non può essere vuoto")
    private String email;
    @NotEmpty(message="password non può essere vuoto")
    private String password;
    private Ruolo ruolo;
}
