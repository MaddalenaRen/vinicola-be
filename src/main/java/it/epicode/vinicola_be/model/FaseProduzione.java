package it.epicode.vinicola_be.model;

import it.epicode.vinicola_be.enumeration.TipoFase;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
public class FaseProduzione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private TipoFase tipoFase;
    private LocalDate dataInizio;
    private LocalDate dataFine;
    private String note;

    @ManyToOne
    @JoinColumn(name = "lotto_id")
    private LottoVino lottoVino;

    @ManyToOne
    @JoinColumn(name = "operatore_id")
    private Operatore operatore;
}
