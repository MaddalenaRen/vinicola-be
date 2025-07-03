package it.epicode.vinicola_be.controller;

import it.epicode.vinicola_be.dto.OperatoreDto;
import it.epicode.vinicola_be.exception.NotFoundException;
import it.epicode.vinicola_be.exception.ValidationException;
import it.epicode.vinicola_be.model.FaseProduzione;
import it.epicode.vinicola_be.model.Operatore;
import it.epicode.vinicola_be.model.Ordine;
import it.epicode.vinicola_be.service.OperatoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/operatori")
public class OperatoreController {

    @Autowired
    private OperatoreService operatoreService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Operatore saveOperatore(@RequestBody @Validated OperatoreDto operatoreDto,
                                   BindingResult bindingResult) throws ValidationException {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .reduce("", (e, s) -> e + s));
        }
        return operatoreService.saveOperatore(operatoreDto);
    }

    @GetMapping("")
    public Page<Operatore> getAllOperatori(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size,
                                           @RequestParam(defaultValue = "id") String sortBy) {
        return operatoreService.getAllOperatori(page, size, sortBy);
    }

    @GetMapping("/{id}")
    public Operatore getOperatore(@PathVariable("id") long idOperatore) throws NotFoundException {
        return operatoreService.getOperatore(idOperatore);
    }

    @PutMapping("/{id}")
    public Operatore updateOperatore(@PathVariable("id") long idOperatore,
                                     @RequestBody @Validated OperatoreDto operatoreDto,
                                     BindingResult bindingResult) throws NotFoundException, ValidationException {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .reduce("", (e, s) -> e + s));
        }
        return operatoreService.updateOperatore(idOperatore, operatoreDto);
    }

    @GetMapping("/{id}/fasi-produzione")
    public List<FaseProduzione> getFasiProduzioneByOperatore(@PathVariable("id") long idOperatore) throws NotFoundException {
        return operatoreService.getFasiProduzioneByOperatore(idOperatore);
    }

    @GetMapping("/{id}/ordini-gestiti")
    public List<Ordine> getOrdiniGestitiByOperatore(@PathVariable("id") long idOperatore) throws NotFoundException {
        return operatoreService.getOrdiniGestitiByOperatore(idOperatore);
    }

    @PostMapping("/{id}/aggiungi-fase")
    public Operatore addFaseProduzione(@PathVariable("id") long idOperatore,
                                       @RequestBody FaseProduzione fase) throws NotFoundException {
        return operatoreService.addFaseProduzione(idOperatore, fase);
    }

    @PostMapping("/{id}/aggiungi-ordine")
    public Operatore addOrdineGestito(@PathVariable("id") long idOperatore,
                                      @RequestBody Ordine ordine) throws NotFoundException {
        return operatoreService.addOrdineGestito(idOperatore, ordine);
    }

    @DeleteMapping("/{id}/rimuovi-fase")
    public Operatore removeFaseProduzione(@PathVariable("id") long idOperatore,
                                          @RequestBody FaseProduzione fase) throws NotFoundException {
        return operatoreService.removeFaseProduzione(idOperatore, fase);
    }

    @DeleteMapping("/{id}/rimuovi-ordine")
    public Operatore removeOrdine(@PathVariable("id") long idOperatore,
                                  @RequestBody Ordine ordine) throws NotFoundException {
        return operatoreService.removeOrdine(idOperatore, ordine);
    }

    @DeleteMapping("/{id}/rimuovi-tutte-fasi")
    public Operatore removeAllFasiProduzione(@PathVariable("id") long idOperatore) throws NotFoundException {
        return operatoreService.removeAllFasiProduzione(idOperatore);
    }

    @DeleteMapping("/{id}/rimuovi-tutti-ordini")
    public Operatore removeAllOrdiniGestiti(@PathVariable("id") long idOperatore) throws NotFoundException {
        return operatoreService.removeAllOrdiniGestiti(idOperatore);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOperatore(@PathVariable("id") long idOperatore) throws NotFoundException {
        operatoreService.deleteOperatore(idOperatore);
    }
}

