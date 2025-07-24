package it.epicode.vinicola_be.controller;

import it.epicode.vinicola_be.dto.LoginDto;
import it.epicode.vinicola_be.dto.UtenteDto;
import it.epicode.vinicola_be.exception.NotFoundException;
import it.epicode.vinicola_be.model.Utente;
import it.epicode.vinicola_be.service.AuthService;
import it.epicode.vinicola_be.service.UtenteService;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private AuthService authService;

    @PostMapping("/auth/register")
    public Utente register(@RequestBody @Validated UtenteDto utenteDto, BindingResult bindingResult) throws ValidationException, NotFoundException {
        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors().stream().
                    map(objectError ->objectError.getDefaultMessage()).
                    reduce("",(e,s)->e+s));
        }
        return utenteService.saveUtente(utenteDto);

    }

    @PostMapping("/auth/login")
    public String login(@RequestBody @Validated LoginDto loginDto, BindingResult bindingResult) throws ValidationException, NotFoundException {
        System.out.println("Chiamata POST /auth/login ricevuta");
        System.out.println("LoginDto: " + loginDto.getEmail() + " / " + loginDto.getPassword());
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult.getAllErrors().stream().
                    map(objectError -> objectError.getDefaultMessage()).
                    reduce("", (e, s) -> e + s));
        }

        return authService.login(loginDto);
    }
}