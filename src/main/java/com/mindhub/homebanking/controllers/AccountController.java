package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toList;


@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/accounts")
    public List<AccountDTO> GetAccounts(){
        List<Account> allAccounts = accountRepository.findAll();

        List<AccountDTO> convertedListAccounts = allAccounts.stream()
                .map(currentAccount -> new AccountDTO(currentAccount))
                .collect(toList());

        return convertedListAccounts;
    }

    @GetMapping("/accounts/{id}")
    public AccountDTO getAccountById(@PathVariable Long id){
        Optional<Account> accountOptional = accountRepository.findById(id);
        return new AccountDTO(accountOptional.get());
    }

    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> createAccount(Authentication authentication) {
        Client client = clientRepository.findByEmail(authentication.getName());

        if (client.getAccounts().size()>=3){
            return new ResponseEntity<>("Ya tienes el máximo de cuentas permitidas", HttpStatus.FORBIDDEN);
        }

        Account account = new Account(getAccountNumber(), LocalDateTime.now(), 0);
        client.addAccount(account);
        accountRepository.save(account);
        return new ResponseEntity<>("Cuenta creada", HttpStatus.CREATED);

    }

    @RequestMapping("/clients/current/accounts")
    public List<AccountDTO> getCurrentAccounts(Authentication authentication) {
        Client client = clientRepository.findByEmail(authentication.getName());
        List<AccountDTO> currentClientAccounts = client.getAccounts().stream()
                .map(account -> new AccountDTO(account)).collect(toList());
        return currentClientAccounts;
    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public String getAccountNumber(){
        String numeroCuenta;
        do {
            numeroCuenta= "VIN-" + String.format("%08d", getRandomNumber(1, 99999999));
        } while (accountRepository.existsByNumber(numeroCuenta));
        return numeroCuenta;
    }

}
