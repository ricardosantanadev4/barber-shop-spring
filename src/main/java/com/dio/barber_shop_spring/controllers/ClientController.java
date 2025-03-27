package com.dio.barber_shop_spring.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dio.barber_shop_spring.models.Client;
import com.dio.barber_shop_spring.services.ClientService;

@RestController
@RequestMapping("clientes")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public ResponseEntity<List<Client>> ListarClientesPaginados() {
        List<Client> clientes = this.clientService.ListarClientesPaginados();
        return new ResponseEntity<List<Client>>(clientes, HttpStatus.OK);
    }
}