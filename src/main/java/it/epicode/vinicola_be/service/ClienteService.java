package it.epicode.vinicola_be.service;

import it.epicode.vinicola_be.dto.ClienteDto;
import it.epicode.vinicola_be.exception.NotFoundException;
import it.epicode.vinicola_be.model.Cliente;
import it.epicode.vinicola_be.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente saveCliente(ClienteDto clienteDto) {
        Cliente cliente = new Cliente();
        cliente.setNome(clienteDto.getNome());
        cliente.setCognome(clienteDto.getCognome());
        cliente.setEmail(clienteDto.getEmail());
        cliente.setCodiceFiscale(clienteDto.getCodiceFiscale());
        cliente.setPartitaIva(clienteDto.getPartitaIva());
        cliente.setTipoCliente(clienteDto.getTipoCliente());
        return clienteRepository.save(cliente);
    }

    public Page<Cliente> getAllClienti(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return clienteRepository.findAll(pageable);
    }

    public Cliente getCliente(long idCliente) throws NotFoundException {
        return clienteRepository.findById(idCliente)
                .orElseThrow(() -> new NotFoundException("Cliente con id: " + idCliente + " non trovato"));
    }

    public Cliente updateCliente(long idCliente, ClienteDto clienteDto) throws NotFoundException {
        Cliente clienteDaAggiornare = getCliente(idCliente);
        clienteDaAggiornare.setNome(clienteDto.getNome());
        clienteDaAggiornare.setCognome(clienteDto.getCognome());
        clienteDaAggiornare.setEmail(clienteDto.getEmail());
        clienteDaAggiornare.setCodiceFiscale(clienteDto.getCodiceFiscale());
        clienteDaAggiornare.setPartitaIva(clienteDto.getPartitaIva());
        clienteDaAggiornare.setTipoCliente(clienteDto.getTipoCliente());
        return clienteRepository.save(clienteDaAggiornare);
    }

    public void deleteCliente(long idCliente) throws NotFoundException {
        Cliente clienteDaRimuovere = getCliente(idCliente);
        clienteRepository.delete(clienteDaRimuovere);
    }
}

