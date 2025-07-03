package it.epicode.vinicola_be.controller;

import it.epicode.vinicola_be.dto.FaseProduzioneDto;
import it.epicode.vinicola_be.exception.NotFoundException;
import it.epicode.vinicola_be.exception.ValidationException;
import it.epicode.vinicola_be.model.FaseProduzione;
import it.epicode.vinicola_be.service.FaseProduzioneService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/fasi-produzione")
public class FaseProduzioneController {

    @Autowired
    private FaseProduzioneService faseProduzioneService;


    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public FaseProduzione saveFaseProduzione(
            @RequestBody @Valid FaseProduzioneDto faseProduzioneDto,
            BindingResult bindingResult,
            @RequestParam Long idLotto,
            @RequestParam Long idOperatore
    ) throws ValidationException, NotFoundException {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .reduce("", (e, s) -> e + s));
        }
        return faseProduzioneService.saveFaseProduzione(faseProduzioneDto, idLotto, idOperatore);
    }


    @GetMapping("")
    public Page<FaseProduzione> getAllFasiProduzione(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        return faseProduzioneService.getAllFasiProduzione(page, size, sortBy);
    }


    @GetMapping("/{id}")
    public FaseProduzione getFaseProduzione(@PathVariable("id") long idFase) throws NotFoundException {
        return faseProduzioneService.getFaseProduzione(idFase);
    }


    @PutMapping("/{id}")
    public FaseProduzione updateFaseProduzione(
            @PathVariable("id") Long idFase,
            @RequestBody @Valid FaseProduzioneDto faseProduzioneDto,
            BindingResult bindingResult
    ) throws ValidationException, NotFoundException {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .reduce("", (e, s) -> e + s));
        }
        return faseProduzioneService.updateFaseProduzione(idFase, faseProduzioneDto);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFaseProduzione(@PathVariable("id") Long idFase) throws NotFoundException {
        faseProduzioneService.deleteFaseProduzione(idFase);
    }


    @GetMapping("/operatore/{idOperatore}")
    public Page<FaseProduzione> findByOperatore(
            @PathVariable Long idOperatore,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        return faseProduzioneService.findByOperatore(idOperatore, page, size, sortBy);
    }


    @GetMapping("/lotto/{idLotto}")
    public Page<FaseProduzione> findByLotto(
            @PathVariable Long idLotto,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        return faseProduzioneService.findByLotto(idLotto, page, size, sortBy);
    }
}
