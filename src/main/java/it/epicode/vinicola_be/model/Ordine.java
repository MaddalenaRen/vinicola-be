package it.epicode.vinicola_be.model;

import it.epicode.vinicola_be.enumeration.StatoOrdine;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

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

    @OneToMany(mappedBy = "ordine", cascade = CascadeType.ALL)
    private List<BottigliaVino> bottiglieVinoOrdinate;
    @ManyToOne
    private Cliente cliente;
    @ManyToOne
    @JoinColumn(name = "operatore_id")
    private Operatore operatore;
}
