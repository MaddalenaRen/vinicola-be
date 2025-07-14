package it.epicode.vinicola_be.repository;

import it.epicode.vinicola_be.model.Ordine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;



public interface OrdineRepository extends JpaRepository<Ordine, Long> {

}
