package com.jwtest.demo.dao;

import com.jwtest.demo.dto.ClientDto;
import com.jwtest.demo.model.Account;
import com.jwtest.demo.model.Client;
import com.jwtest.demo.repositories.AccountRepository;
import com.jwtest.demo.repositories.ClientRepository;
import com.jwtest.demo.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;

@Service
public class ClientsService {

    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public ClientsService(ClientRepository clientRepository, @Autowired(required = false) PasswordEncoder passwordEncoder, AccountRepository accountRepository, RoleRepository roleRepository) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional
    public Client registerNewClientAccount(ClientDto clientDto) {

        Client client = new Client();
        client.setName(clientDto.getName());
        client.setEmail(clientDto.getEmail());
        client.setPassword(passwordEncoder.encode(clientDto.getPassword()));

        client.getRoles().add(roleRepository.findByRoleName("ROLE_USER"));

        Client saved = clientRepository.save(client);
        Account account = new Account(saved, 0, Instant.now(), "Savings Account", "100");
        accountRepository.save(account);

        return saved;
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }
}
