package it.epicode.vinicola_be.model;

import it.epicode.vinicola_be.enumeration.StatoOrdine;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;


@Data
@Entity
public class Ordine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int quantita;
    @Enumerated(EnumType.STRING)
    private StatoOrdine stato;
    private LocalDate dataOrdine;
    private LocalDate dataConsegna;

    @ManyToOne
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "operatore_id")
    private Operatore operatore;

    @ManyToOne
    @JoinColumn(name = "etichetta_id")
    private Etichetta etichetta;
}
