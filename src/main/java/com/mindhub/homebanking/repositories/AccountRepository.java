package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface AccountRepository extends JpaRepository<Account, Long> {
    /*Account findByNumber(String number);*/
    boolean existsByNumber(String accountNumber);

    boolean existsByNumberAndOwner (String number, Client owner);

    Account findByNumber (String number);

    Account findByNumberAndOwner (String number, Client owner);

    Account findByIdAndOwner (Long Id, Client owner);

}