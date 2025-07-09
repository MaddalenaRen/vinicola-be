package it.epicode.vinicola_be.model;

import it.epicode.vinicola_be.enumeration.TipologiaVino;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;


@Data
@Entity
public class Etichetta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private TipologiaVino tipologiaVino;
    private double gradazioneAlcolica;
    private LocalDate dataImbottigliamento;

    @ManyToOne
    @JoinColumn(name = "cantina_id")
    private Cantina cantina;

    @OneToMany(mappedBy = "etichetta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LottoVino> lotti;

    @ManyToMany
    @JoinTable(name = "ordine_etichetta")
    private Ordine ordine;

    @ManyToOne
    @JoinTable(name = "ordine_etichetta")
    private Etichetta etichetta;

}
