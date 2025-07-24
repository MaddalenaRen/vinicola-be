package it.epicode.vinicola_be.model;


import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
public class LottoVino {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantita;
    private String nome;
    private int annata;
    private String varietaUva;


    private String faseProduzione;

    @ManyToOne
    @JoinColumn(name = "etichetta_id")
    private Etichetta etichetta;
}
