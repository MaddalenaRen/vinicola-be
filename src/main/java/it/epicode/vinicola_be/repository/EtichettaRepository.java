package it.epicode.vinicola_be.repository;

import it.epicode.vinicola_be.model.Etichetta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EtichettaRepository extends JpaRepository<Etichetta, Long> {
    Optional<Etichetta> findByNomeEtichetta(String nomeEtichetta);
}
