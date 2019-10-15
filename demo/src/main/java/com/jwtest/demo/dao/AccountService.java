package com.jwtest.demo.dao;

import com.jwtest.demo.dto.AccountDto;
import com.jwtest.demo.exceptions.ForbiddenActionException;
import com.jwtest.demo.exceptions.IncorrectOperation;
import com.jwtest.demo.exceptions.ResourceNotFound;
import com.jwtest.demo.model.Account;
import com.jwtest.demo.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountDto getAccountForNumber(String accountNumber, String clientName) {
        return accountRepository.findAccountByNumber(accountNumber)
                .map(account -> new AccountDto(
                        account.getClient().getName(), account.getDateCreated(), account.getMoney(), account.getType(), account.getNumber()))
                .orElseThrow(ResourceNotFound::new);
    }

    @Transactional
    public void transferMoney(int moneyToAdd, String accountNumberFrom, String accountNumberTo, String clientName) {
        Optional<Account> accountFromOpt = accountRepository.findAccountByNumber(accountNumberFrom);
        if (accountFromOpt.isPresent()) {
            Account accountFrom = accountFromOpt.get();
            if (moneyToAdd > accountFrom.getMoney()) {
                throw new IncorrectOperation();
            }
            if (!accountFrom.getClient().getName().equals(clientName)) {
                throw new ForbiddenActionException();
            }
            Optional<Account> accountToOpt = accountRepository.findAccountByNumber(accountNumberTo);
            if (!accountToOpt.isPresent()) {
                throw new IncorrectOperation();
            }
            Account accountTo = accountToOpt.get();
            accountTo.setMoney(accountTo.getMoney() + moneyToAdd);
            accountFrom.setMoney(accountFrom.getMoney() - moneyToAdd);
        }
    }

    public List<AccountDto> getAccounts() {
        return accountRepository.findWithClientFetched()
                .stream()
                .map(account -> new AccountDto(
                        account.getClient().getName(), account.getDateCreated(), account.getMoney(), account.getType(), account.getNumber()))
                .collect(toList());
    }
}
