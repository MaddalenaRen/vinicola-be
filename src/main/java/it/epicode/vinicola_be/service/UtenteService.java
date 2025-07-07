package it.epicode.vinicola_be.service;

import it.epicode.vinicola_be.dto.UtenteDto;
import it.epicode.vinicola_be.enumeration.Ruolo;
import it.epicode.vinicola_be.exception.NotFoundException;
import it.epicode.vinicola_be.model.Utente;
import it.epicode.vinicola_be.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UtenteService {

    @Autowired
    UtenteRepository utenteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public Utente saveUtente(UtenteDto utenteDto){
        Utente utente = new Utente();


        utente.setEmail(utenteDto.getEmail());
        utente.setPassword(passwordEncoder.encode(utenteDto.getPassword()));


        Ruolo ruolo = utenteDto.getRuolo() != null ? utenteDto.getRuolo() : Ruolo.ADMIN;
        utente.setRuolo(ruolo);

        return utenteRepository.save(utente);
    }

    public Page<Utente> getAllUtenti(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return utenteRepository.findAll(pageable);

    }

    public Utente getUtente(long id) throws NotFoundException {
        return utenteRepository.findById(id).
                orElseThrow(() -> new NotFoundException("Utente con id:" + id + " non trovato"));
    }

    public Utente updateUtente(long id, UtenteDto utenteDto) throws NotFoundException {
        Utente utenteDaAggiornare = getUtente(id);


        utenteDaAggiornare.setEmail(utenteDto.getEmail());
        if(!passwordEncoder.matches(utenteDto.getPassword(), utenteDaAggiornare.getPassword())) {
            utenteDaAggiornare.setPassword(passwordEncoder.encode(utenteDto.getPassword()));
        }

        return utenteRepository.save(utenteDaAggiornare);
    }


    public void deleteUtente(int id) throws NotFoundException {
        Utente utenteDaRimuovere = getUtente(id);

        utenteRepository.delete(utenteDaRimuovere);
    }
}
