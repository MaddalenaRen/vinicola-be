package it.epicode.vinicola_be.repository;

import it.epicode.vinicola_be.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

}
