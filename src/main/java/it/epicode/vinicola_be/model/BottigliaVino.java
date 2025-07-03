package it.epicode.vinicola_be.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class BottigliaVino {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String immagineUrl;



    @ManyToOne
    @JoinColumn(name = "etichetta_id")
    private Etichetta etichetta;

    @ManyToOne
    @JoinColumn(name = "lotto_id")
    private LottoVino lotto;

    @ManyToOne
    @JoinColumn(name = "ordine_id")
    private Ordine ordine;


}
