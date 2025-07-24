package it.epicode.vinicola_be.controller;

import it.epicode.vinicola_be.dto.ClienteDto;
import it.epicode.vinicola_be.dto.OrdineDto;
import it.epicode.vinicola_be.exception.NotFoundException;
import it.epicode.vinicola_be.exception.ValidationException;
import it.epicode.vinicola_be.model.Cliente;
import it.epicode.vinicola_be.model.Ordine;
import it.epicode.vinicola_be.service.ClienteService;
import it.epicode.vinicola_be.service.OrdineService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@PreAuthorize("hasRole('ADMIN')")

@RestController
@RequestMapping(path = "/clienti")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente saveCliente(@RequestBody @Validated ClienteDto clienteDto,
                               BindingResult bindingResult)
            throws ValidationException, NotFoundException {
        if (bindingResult.hasErrors()) {
            System.out.println("Cliente ricevuto: " + clienteDto);
            throw new ValidationException(bindingResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .reduce("", (e, s) -> e + s));

        }
        return clienteService.saveCliente(clienteDto);
    }

    @GetMapping("")
    public Page<Cliente> getAllClienti(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(required = false) String nome
    ){
        if(nome != null && !nome.isEmpty()) {
            return clienteService.searchByNome(nome, page, size, sortBy);
        } else {
            return clienteService.getAllClienti(page, size, sortBy);
        }
    }

    @GetMapping("/{id}")
    public Cliente getCliente(@PathVariable("id") long idCliente)throws NotFoundException {
        return clienteService.getCliente(idCliente);
    }

    @PutMapping("/{id}")
    public Cliente updateCliente(@PathVariable("id") long idCliente,
                               @RequestBody @Validated ClienteDto clienteDto,
                               BindingResult bindingResult) throws NotFoundException, ValidationException {
        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors().stream().
                    map(objectError -> objectError.getDefaultMessage()).
                    reduce("" , (e, s) -> e+ s ));
        }
        return clienteService.updateCliente(idCliente, clienteDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCliente(@PathVariable("id") Long idCliente) throws NotFoundException {
        clienteService.deleteCliente(idCliente);
    }
}
