package com.dio.barber_shop_spring.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.dio.barber_shop_spring.models.Client;
import com.dio.barber_shop_spring.repositories.ClientRepository;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client createClient(Client client) {

        if (this.clientRepository.existsByNomeOrEmail(client.getNome(), client.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Já existe um cadastro com o nome ou e-mail fornecido.");
        }
        ;
        return this.clientRepository.save(client);
    }

    public Page<Client> clientesPaginados(int pageIndex, int pageSize, String filter) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);

        if (this.verificarStrigVaziaOuNula(filter)) {
            Page<Client> clientPage = this.clientRepository.findAll(pageable);
            return clientPage;
        }

        return this.clientRepository.filterClients(filter, pageable);
    }

    public boolean verificarStrigVaziaOuNula(String string) {
        return string == null || string.trim().isEmpty();
    }

    public Client getClientById(Long id) {
        Optional<Client> client = this.clientRepository.findById(id);
        if (client.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Cliente não encontrado com id: " + id + " Fornecido.");
        }
        return client.get();
    }

}
