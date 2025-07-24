package it.epicode.vinicola_be.controller;

import it.epicode.vinicola_be.dto.EtichettaDto;
import it.epicode.vinicola_be.exception.NotFoundException;
import it.epicode.vinicola_be.exception.ValidationException;
import it.epicode.vinicola_be.model.Etichetta;
import it.epicode.vinicola_be.service.EtichettaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@PreAuthorize("hasRole('ADMIN')")

@RestController
@RequestMapping(path = "/etichette")
public class EtichettaController {
    @Autowired
    private EtichettaService etichettaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Etichetta saveEtichetta(@RequestBody @Validated EtichettaDto etichettaDto,
                                   BindingResult bindingResult)
            throws ValidationException, NotFoundException {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .reduce("", (e, s) -> e + s));
        }
        return etichettaService.saveEtichetta(etichettaDto);
    }

    @PostMapping("/{etichettaId}/ordini/{ordineId}")
    public ResponseEntity<String> aggiungiOrdineAEtichetta(@PathVariable Long etichettaId,
                                                           @PathVariable Long ordineId) throws NotFoundException {
        etichettaService.associaEtichettaAOrdine(etichettaId, ordineId);
        return ResponseEntity.ok("Ordine aggiunto all'etichetta con successo.");
    }


    @GetMapping("")
    public Page<Etichetta> getAllEtichetta(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(required = false) String nomeEtichetta
    ){
        if(nomeEtichetta != null && !nomeEtichetta.isEmpty()) {
            return etichettaService.searchByNomeEtichetta(nomeEtichetta, page, size, sortBy);
        } else {
            return etichettaService.getAllEtichette(page, size, sortBy);
        }
    }

    @GetMapping("/{id}")
    public Etichetta getEtichette(@PathVariable("id") long idEtichetta)throws NotFoundException {
        return etichettaService.getEtichetta(idEtichetta);
    }

    @PutMapping("/{id}")
    public Etichetta updateEtichetta(@PathVariable("id") long idEtichetta,
                                 @RequestBody @Validated EtichettaDto etichettaDto,
                                 BindingResult bindingResult) throws NotFoundException, ValidationException {
        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors().stream().
                    map(objectError -> objectError.getDefaultMessage()).
                    reduce("" , (e, s) -> e+ s ));
        }
        return etichettaService.updateEtichetta(idEtichetta, etichettaDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEtichetta(@PathVariable("id") Long idEtichetta) throws NotFoundException {
        etichettaService.deleteEtichetta(idEtichetta);
    }
}
