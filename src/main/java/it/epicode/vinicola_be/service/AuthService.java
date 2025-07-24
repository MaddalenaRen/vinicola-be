package it.epicode.vinicola_be.service;

import it.epicode.vinicola_be.dto.LoginDto;
import it.epicode.vinicola_be.exception.NotFoundException;
import it.epicode.vinicola_be.model.Utente;
import it.epicode.vinicola_be.repository.UtenteRepository;
import it.epicode.vinicola_be.security.JwtTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UtenteRepository userRepository;

    @Autowired
    private JwtTool jwtTool;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String login(LoginDto loginDto) throws NotFoundException {

        System.out.println("Tentativo login con email: " + loginDto.getEmail());
        System.out.println("Tentativo login con password: " + loginDto.getPassword());
        Utente utente = userRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new NotFoundException("Utente con email: " + loginDto.getEmail() + " non trovato"));

        System.out.println("Login tentato con: " + loginDto.getEmail() + " / " + loginDto.getPassword());
        System.out.println("Password nel DB (hash): " + utente.getPassword());

        System.out.println("use to save password encoded: " + passwordEncoder.encode(loginDto.getPassword()));

        if (passwordEncoder.matches(loginDto.getPassword(), utente.getPassword())) {
            return jwtTool.createToken(utente);
        } else {
            throw new NotFoundException("Utente con questa email/password non trovati");
        }
    }
}
