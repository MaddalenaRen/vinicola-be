package it.epicode.vinicola_be.repository;

import it.epicode.vinicola_be.model.Operatore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OperatoreRepository extends JpaRepository<Operatore, Long> {
    List<Operatore> findByReparto(String reparto);


}
