package it.epicode.vinicola_be.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private String nomeEtichetta;
    private TipologiaVino tipologiaVino;
    private double gradazioneAlcolica;
    private LocalDate dataImbottigliamento;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "cantina_id")
    private Cantina cantina;

    @OneToMany(mappedBy = "etichetta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LottoVino> lotti;


    @ManyToMany
    @JoinTable(
            name = "etichetta_ordine",
            joinColumns = @JoinColumn(name = "etichetta_id"),
            inverseJoinColumns = @JoinColumn(name = "ordine_id")
    )
    private List<Ordine> ordini;


    public void addOrdine(Ordine ordine) {
        this.ordini.add(ordine);
        ordine.getEtichette().add(this);
    }

    public void removeOrdine(Ordine ordine) {
        this.ordini.remove(ordine);
        ordine.getEtichette().remove(this);
    }

}
