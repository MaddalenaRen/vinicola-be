package it.epicode.vinicola_be.controller;

import it.epicode.vinicola_be.dto.OrdineDto;
import it.epicode.vinicola_be.enumeration.StatoOrdine;
import it.epicode.vinicola_be.exception.NotFoundException;
import it.epicode.vinicola_be.exception.ValidationException;
import it.epicode.vinicola_be.model.Ordine;
import it.epicode.vinicola_be.service.OrdineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping(path = "/ordini")
public class OrdineController {

    @Autowired
    private OrdineService ordineService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Ordine saveOrdine(@RequestBody @Validated OrdineDto ordineDto,
                             BindingResult bindingResult)
            throws ValidationException, NotFoundException {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .reduce("", (e, s) -> e + s));
        }
        return ordineService.saveOrdine(ordineDto);
    }

    @GetMapping("")
    public Page<Ordine> getAllOrdini(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size,
                                     @RequestParam(defaultValue = "id") String sortBy){

        return ordineService.getAllOrdini(page,size, sortBy);
    }

    @GetMapping("/{id}")
    public Ordine getOrdine(@PathVariable("id") long idOrdine)throws NotFoundException {
        return ordineService.getOrdine(idOrdine);
    }

    @PutMapping("/{idOrdine}")
    public Ordine updateOrdine(@PathVariable long idOrdine,
                               @RequestBody @Validated OrdineDto ordineDto,
                               BindingResult bindingResult) throws NotFoundException, ValidationException {
        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors().stream().
                    map(objectError -> objectError.getDefaultMessage()).
                    reduce("" , (e, s) -> e+ s ));
        }
        return ordineService.updateOrdine(ordineDto, idOrdine);
    }


    @PatchMapping("/{id}/stato")
    public Ordine aggiornaStatoOrdine(@PathVariable("id") Long idOrdine,
                                      @RequestParam StatoOrdine nuovoStato) throws NotFoundException {
        return ordineService.aggiornaStatoOrdine(idOrdine, nuovoStato);
    }

    @PatchMapping("/{id}/assegna-operatore")
    public Ordine aggiornaOperatore(@PathVariable("id") Long idOrdine,
                                    @RequestParam Long idOperatore) throws NotFoundException {
        return ordineService.aggiornaOperatore(idOrdine, idOperatore);
    }

    @PatchMapping("/{id}/aggiorna-data-consegna")
    public Ordine aggiornaDataConsegna(@PathVariable("id") Long idOrdine,
                                       @RequestBody LocalDate dataConsegna) throws NotFoundException {
        return ordineService.aggiornaDataConsegna(idOrdine, dataConsegna);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrdine(@PathVariable("id") Long idOrdine) throws NotFoundException {
        ordineService.deleteOrdine(idOrdine);
    }


}
