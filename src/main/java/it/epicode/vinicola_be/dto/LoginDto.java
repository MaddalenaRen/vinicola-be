package it.epicode.vinicola_be.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginDto {

    @Email(message = "Inserisci un indirizzo email valido")
    @NotEmpty(message="indirizzo email non può essere vuoto")
    private String email;

    @NotEmpty(message="password non può essere vuoto")
    private String password;

}