package it.epicode.vinicola_be.repository;

import it.epicode.vinicola_be.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtenteRepository extends JpaRepository<Utente,Long> {
}
