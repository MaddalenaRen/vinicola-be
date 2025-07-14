package it.epicode.vinicola_be.controller;


import it.epicode.vinicola_be.dto.LottoVinoDto;
import it.epicode.vinicola_be.exception.NotFoundException;
import it.epicode.vinicola_be.exception.ValidationException;
import it.epicode.vinicola_be.model.LottoVino;
import it.epicode.vinicola_be.service.LottoVinoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/lotti-vino")
public class LottoVinoController {
    @Autowired
    private LottoVinoService lottoVinoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LottoVino saveLottoVino(@RequestBody @Validated LottoVinoDto lottoVinoDto,
                                   BindingResult bindingResult)
            throws ValidationException, NotFoundException {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .reduce("", (e, s) -> e + s));
        }
        return lottoVinoService.saveLottoVino(lottoVinoDto);
    }

    @GetMapping("")
    public Page<LottoVino> getAllLotti(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size,
                                       @RequestParam(defaultValue = "id") String sortBy){

        return lottoVinoService.getAllLotti(page,size, sortBy);
    }

    @GetMapping("/{id}")
    public LottoVino getLotto(@PathVariable("id") long idLotto)throws NotFoundException {
        return lottoVinoService.getLotto(idLotto);
    }

    @PutMapping("/{id}")
    public LottoVino updateLotto(@PathVariable("id") long idLotto,
                                 @RequestBody @Validated LottoVinoDto lottoVinoDto,
                                 BindingResult bindingResult) throws NotFoundException, ValidationException {
        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors().stream().
                    map(objectError -> objectError.getDefaultMessage()).
                    reduce("" , (e, s) -> e+ s ));
        }
        return lottoVinoService.updateLotto(idLotto, lottoVinoDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLotto(@PathVariable("id") Long idLotto) throws NotFoundException {
        lottoVinoService.deleteLotto(idLotto);
    }
}
