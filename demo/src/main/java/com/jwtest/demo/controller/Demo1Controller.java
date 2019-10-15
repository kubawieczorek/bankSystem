package com.jwtest.demo.controller;

import com.jwtest.demo.dao.AccountService;
import com.jwtest.demo.dao.ClientsService;
import com.jwtest.demo.dto.AccountDto;
import com.jwtest.demo.dto.ClientDto;
import com.jwtest.demo.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class Demo1Controller {

    private final AccountService accountService;
    private final ClientsService clientsService;

    @Autowired
    public Demo1Controller(AccountService accountService, ClientsService clientsService) {
        this.accountService = accountService;
        this.clientsService = clientsService;
    }

    @RequestMapping(value = "/accounts", method = RequestMethod.GET, produces = "application/json")
    @PostFilter("hasRole('ADMIN') or filterObject.clientName == authentication.name")
    public List<AccountDto> getAccounts() {
        return accountService.getAccounts();
    }

    @RequestMapping(value = "/accounts/{accountNumber}", method = RequestMethod.GET, produces = "application/json")
    @PreAuthorize("hasRole('USER')")
    public AccountDto getAccount(@PathVariable String accountNumber) {

    }

    @RequestMapping(value = "/account/{accountNumber}/transfer", method = RequestMethod.POST)
    @PreAuthorize("hasRole('USER') or oauth2.hasScope('write')")
    public void addMoney(@RequestParam(name = "amount", required = true) int amount,
                         @RequestParam(name = "targetAccount", required = true) String targetAccount,
                         @PathVariable String accountNumber) {
        accountService.transferMoney(amount, accountNumber, targetAccount, getLoggedUsername());
    }

    @RequestMapping(value = "/clients", method = RequestMethod.GET)
    @PostFilter("hasRole('ADMIN') or filterObject.name == authentication.name")
    public List<Client> getClients() {
        return clientsService.getAllClients();
    }

    @RequestMapping(value = "/client/registration", method = RequestMethod.POST, consumes = "application/json")
    public void registerUserAccount(@RequestBody ClientDto accountDto) {
        clientsService.registerNewClientAccount(accountDto);
    }

    @RequestMapping(value = "/myAccount/client", method = RequestMethod.GET, produces = "application/json")
    public Map<String, String> getClient() {
        HashMap<String, String> map = new HashMap<>();
        map.put("name", getLoggedUsername());
        return map;
    }

    @RequestMapping(value = "/greeting", method = RequestMethod.GET, produces = "application/json")
    public Object greeting() {
        return "Hello!";
    }

    private String getLoggedUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }
}
