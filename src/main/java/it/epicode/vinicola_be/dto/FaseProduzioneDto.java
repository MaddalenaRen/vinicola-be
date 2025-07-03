package it.epicode.vinicola_be.dto;

import it.epicode.vinicola_be.enumeration.TipoFase;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
@Data
public class FaseProduzioneDto {

    @NotNull(message = "È obbligatorio specificare il tipo di fase")
    private TipoFase tipoFase;

    @NotNull(message = "È obbligatorio specificare la data di inizio")
    private LocalDate dataInizio;

    private LocalDate dataFine;

    private String note;

    @NotNull(message = "È obbligatorio specificare l'id del lotto di vino")
    private Long idLottoVino;

    @NotNull(message = "È obbligatorio specificare l'id dell'operatore")
    private Long idOperatore;
}
