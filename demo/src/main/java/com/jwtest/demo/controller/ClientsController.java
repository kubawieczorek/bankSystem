package com.jwtest.demo.controller;

import com.jwtest.demo.dao.ClientsService;
import com.jwtest.demo.dto.ClientDto;
import com.jwtest.demo.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientsController {

    private final ClientsService clientsService;

    @Autowired
    public ClientsController(ClientsService clientsService) {
        this.clientsService = clientsService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @PostFilter("hasRole('ADMIN') or filterObject.name == authentication.name")
    public List<Client> getClients() {
        return clientsService.getAllClients();
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST, consumes = "application/json")
    public void registerUserAccount(@RequestBody ClientDto accountDto) {
        clientsService.registerNewClientAccount(accountDto);
    }
}
