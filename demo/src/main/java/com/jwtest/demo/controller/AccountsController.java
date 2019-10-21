package com.jwtest.demo.controller;

import com.jwtest.demo.dao.AccountService;
import com.jwtest.demo.dto.AccountDto;
import com.jwtest.demo.dto.TransferDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountsController {

    private final AccountService accountService;

    @Autowired
    public AccountsController(AccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    @PostFilter("hasRole('ADMIN') or filterObject.clientName == authentication.name")
    public List<AccountDto> getAccounts() {
        return accountService.getAccounts();
    }

    @RequestMapping(value = "/{accountNumber}", method = RequestMethod.GET, produces = "application/json")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public AccountDto getAccount(@PathVariable String accountNumber) {
        return accountService.getAccountForNumber(accountNumber, getLoggedUsername());
    }

    @RequestMapping(value = "/{accountNumber}/transfer", method = RequestMethod.POST)
    @PreAuthorize("hasRole('USER') and (not #oauth2.isOAuth() or #oauth2.hasScope('write'))")
    public void addMoney(@RequestBody TransferDto transferDto,
                         @PathVariable String accountNumber) {
        accountService.transferMoney(transferDto, accountNumber, getLoggedUsername());
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
