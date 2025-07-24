package it.epicode.vinicola_be.repository;

import it.epicode.vinicola_be.model.Operatore;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface OperatoreRepository extends JpaRepository<Operatore, Long> {

    List<Operatore> findByReparto(String reparto);

    Page<Operatore> findByNomeContainingIgnoreCase(String nome, Pageable pageable);


}
