package it.epicode.vinicola_be.model;

import it.epicode.vinicola_be.enumeration.TipoCliente;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nome;
    private String cognome;
    private String email;
    private String codiceFiscale;
    private String partitaIva;

    @Enumerated(EnumType.STRING)
    private TipoCliente tipoCliente;

    @OneToMany(mappedBy = "cliente")
    private List<Ordine> ordini;
}
