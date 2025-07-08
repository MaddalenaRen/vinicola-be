package it.epicode.vinicola_be.model;

import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;

public class OrdineEtichetta {

    @ManyToOne
    @JoinTable(name = "ordine_id")
    private Ordine ordine;

    @ManyToOne
    @JoinTable(name = "etichetta_id")
    private Etichetta etichetta;


}
