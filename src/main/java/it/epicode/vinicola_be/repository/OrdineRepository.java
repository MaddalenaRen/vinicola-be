package it.epicode.vinicola_be.repository;

import it.epicode.vinicola_be.model.Ordine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface OrdineRepository extends JpaRepository<Ordine, Long> {
    @Query("SELECT o FROM Ordine o WHERE " +
            "LOWER(o.cliente.nome) LIKE LOWER(CONCAT('%', :cliente, '%')) " +
            "OR LOWER(o.cliente.cognome) LIKE LOWER(CONCAT('%', :cliente, '%'))")
    Page<Ordine> findByClienteNomeOrCognome(@Param("cliente") String cliente, Pageable pageable);

}
