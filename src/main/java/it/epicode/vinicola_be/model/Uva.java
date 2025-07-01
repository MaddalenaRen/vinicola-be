package it.epicode.vinicola_be.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Uva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String tipoUva;
    private String vignetoDiProveninza;
    private int gradoZucchero;
    private int quantitaRaccolta;

    @ManyToOne
    @JoinColumn(name = "cantina_id")
    private Cantina cantina;

    @OneToOne(mappedBy = "uva")
    private LottoVino lottoVino;
}
