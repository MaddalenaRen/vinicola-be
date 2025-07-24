package it.epicode.vinicola_be.dto;


import it.epicode.vinicola_be.model.Cliente;
import it.epicode.vinicola_be.model.Etichetta;
import it.epicode.vinicola_be.model.Operatore;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class OrdineDto {
    @NotNull(message = "La quantità è obbligatoria")
    @Min(value = 1, message = "La quantità deve essere almeno 1")
    private int quantita;

    @NotNull(message = "La data ordine è obbligatoria")
    private LocalDate dataOrdine;

    private LocalDate dataConsegna;

    @NotNull(message = "Cliente è obbligatorio")
    private Cliente cliente;

    private Operatore operatore;

    @NotNull(message = "Stato è obbligatorio")
    private String stato;

    @NotNull(message = "Etichetta è obbligatorio")
    private Etichetta etichetta;
}
