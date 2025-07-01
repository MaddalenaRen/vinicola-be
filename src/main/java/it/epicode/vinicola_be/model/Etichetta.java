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

    @OneToMany(mappedBy = "etichetta")
    private List<BottigliaVino> bottiglie;

}
