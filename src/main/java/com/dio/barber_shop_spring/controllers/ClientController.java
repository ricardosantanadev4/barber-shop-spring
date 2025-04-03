package com.dio.barber_shop_spring.controllers;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @PostMapping("/criar")
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        Client body = this.clientService.createClient(client);
        return new ResponseEntity<Client>(body, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<Client>> clientesPaginados(@RequestParam(defaultValue = "0") int pageIndex,
            @RequestParam(defaultValue = "10") int pageSize, @RequestParam(required = false) String filter) {
        Page<Client> clientPage = this.clientService.clientesPaginados(pageIndex, pageSize, filter);
        return new ResponseEntity<Page<Client>>(clientPage, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Long id) {
        Client client = this.clientService.getClientById(id);
        return new ResponseEntity<Client>(client, HttpStatus.OK);
    }

}