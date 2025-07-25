package it.epicode.vinicola_be.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Operatore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String cognome;
    private String reparto;
    private String numeroTelefono;

    @OneToOne
    @JoinColumn(name = "utente_id")
    private Utente utente;

    @OneToMany(mappedBy = "operatore")
    private List<FaseProduzione> fasiGestite;

    @OneToMany(mappedBy = "operatore")
    @JsonIgnore
    private List<Ordine> ordiniGestiti;

}
