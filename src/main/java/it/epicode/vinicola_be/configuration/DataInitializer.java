package it.epicode.vinicola_be.configuration;

import it.epicode.vinicola_be.dto.UtenteDto;
import it.epicode.vinicola_be.enumeration.Ruolo;
import it.epicode.vinicola_be.service.UtenteService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final UtenteService utenteService;

    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initData() {
        return args -> {
            // Admin predefinito
            if (!utenteService.existsByEmail("maddalena@email.com")) {
                UtenteDto admin = new UtenteDto();
                admin.setEmail("maddalena@email.com");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRuolo(Ruolo.ADMIN);
                utenteService.saveUtente(admin);
                System.out.println("Admin creato con password criptata.");
            }

        };
    }
}


