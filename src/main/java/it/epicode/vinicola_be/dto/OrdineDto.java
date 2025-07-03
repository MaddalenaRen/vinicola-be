package it.epicode.vinicola_be.dto;


import it.epicode.vinicola_be.enumeration.StatoOrdine;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

    @NotNull(message = "ClienteId è obbligatorio")
    private Long clienteId;

    private Long operatoreId;
}
