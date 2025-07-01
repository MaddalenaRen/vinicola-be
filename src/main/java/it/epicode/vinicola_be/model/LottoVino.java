package it.epicode.vinicola_be.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

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


    @OneToOne
    @JoinColumn(name = "uva_id")
    private Uva uva;

    @OneToMany(mappedBy = "lottoVino", cascade = CascadeType.ALL)
    private List<FaseProduzione> fasiProduzione;

    @OneToMany(mappedBy = "lotto", cascade = CascadeType.ALL)
    private List<BottigliaVino>bottiglieVino;
}
