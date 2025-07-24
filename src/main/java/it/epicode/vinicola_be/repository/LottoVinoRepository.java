package it.epicode.vinicola_be.repository;

import it.epicode.vinicola_be.model.Cliente;
import it.epicode.vinicola_be.model.LottoVino;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LottoVinoRepository extends JpaRepository<LottoVino,Long> {

    Page<LottoVino> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
}
