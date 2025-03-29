package com.dio.barber_shop_spring.controllers;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping
    public ResponseEntity<Page<Client>> listarClientesPaginados(@RequestParam(defaultValue = "0") int pageIndex,
            @RequestParam(defaultValue = "10") int pageSize , @RequestParam(required = false) String filter) {
        System.out.println("Page Index: " + pageIndex + "Page Size: " + pageSize);
        Page<Client> clientPage = this.clientService.listarClientesPaginados(pageIndex, pageSize, filter);
        return new ResponseEntity<Page<Client>>(clientPage, HttpStatus.OK);
    }

}