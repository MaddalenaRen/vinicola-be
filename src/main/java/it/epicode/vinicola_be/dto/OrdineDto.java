package it.epicode.vinicola_be.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

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

    @NotEmpty(message = "Etichetta è obbligatorio")
    private List<String> etichette;
}
