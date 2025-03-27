package com.dio.barber_shop_spring.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dio.barber_shop_spring.models.Client;
import com.dio.barber_shop_spring.repositories.ClientRepository;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> ListarClientesPaginados() {
        return this.clientRepository.findAll();
    }

}
