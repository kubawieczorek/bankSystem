package com.jwtest.demo.service;

import com.jwtest.demo.model.Client;
import com.jwtest.demo.model.Role;
import com.jwtest.demo.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class DemoUserDetailsService implements UserDetailsService {

    private final ClientRepository clientRepository;

    @Autowired
    public DemoUserDetailsService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Transactional
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        Optional<Client> user = clientRepository.findByName(username);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException(
                    "No user found with username: " + username);
        }
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
        return new org.springframework.security.core.userdetails.User(
                user.get().getName(),
                user.get().getPassword(),
                enabled,
                accountNonExpired,
                credentialsNonExpired,
                accountNonLocked,
                getAuthorities(user.get().getRoles()));
    }

    private static List<GrantedAuthority> getAuthorities(Set<Role> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        }
        return authorities;
    }
}
