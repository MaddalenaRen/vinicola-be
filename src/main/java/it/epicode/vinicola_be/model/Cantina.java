package it.epicode.vinicola_be.model;

import it.epicode.vinicola_be.enumeration.Regione;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Cantina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nome;
    @Enumerated(EnumType.STRING)
    private Regione regione;

    @OneToMany(mappedBy = "cantina", cascade = CascadeType.ALL)
    private List<Etichetta> etichette;


}
