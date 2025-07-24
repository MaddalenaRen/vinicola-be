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



    @OneToMany(mappedBy = "etichetta", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<LottoVino> lotti;


    @OneToMany(mappedBy = "etichetta", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Ordine> ordini;

}
