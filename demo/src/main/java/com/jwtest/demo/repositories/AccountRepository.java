package com.jwtest.demo.repositories;

import com.jwtest.demo.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    Optional<Account> findByClient_Name(@Param("name") String name);

    @Query("select a from Account as a " +
            "join fetch a.client")
    List<Account> findWithClientFetched();

    Optional<Account> findAccountByNumber(@Param("number") String number);
}
