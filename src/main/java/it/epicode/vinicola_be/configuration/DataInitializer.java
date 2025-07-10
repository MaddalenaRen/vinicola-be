package it.epicode.vinicola_be.configuration;

import it.epicode.vinicola_be.enumeration.Regione;
import it.epicode.vinicola_be.model.Cantina;
import it.epicode.vinicola_be.repository.CantinaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final CantinaRepository cantinaRepo;

    @Bean
    public CommandLineRunner initData() {
        return args -> {
            if (cantinaRepo.count() == 0) {
                Cantina cantina = new Cantina();
                cantina.setNome("Santi Quaranta");
                cantina.setRegione(Regione.CAMPANIA);
                cantinaRepo.save(cantina);
            }
        };
    }
}
