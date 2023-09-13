package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.utils.AccountUtils;
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
    private AccountService accountService;

    @Autowired
    private ClientService clientService;

    @GetMapping("/accounts")
    public List<AccountDTO> GetAccounts(){
        return accountService.GetAccountsDTO();
    }


    /*
    @GetMapping("/accounts/{id}")
    public AccountDTO getAccountById(@PathVariable Long id, Authentication authentication){
        Client client = clientRepository.findByEmail(authentication.getName());
        return new AccountDTO(accountRepository.findByIdAndOwner(id, client));
     */
    @GetMapping("/accounts/{id}")
    public ResponseEntity<Object> getAccountById(@PathVariable Long id, Authentication authentication){
        Client client = clientService.findByEmail(authentication.getName());
        Account account = accountService.findByIdAndOwner(id, client);
        if (account==null){
            return new ResponseEntity<>("La cuenta no pertenece al cliente", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(new AccountDTO(account), HttpStatus.ACCEPTED);
    }

    @GetMapping("/clients/current/accounts")
    public List<AccountDTO> getCurrentAccounts(Authentication authentication) {
        Client client = clientService.findByEmail(authentication.getName());
        List<AccountDTO> currentClientAccounts = client.getAccounts().stream()
                .map(account -> new AccountDTO(account)).collect(toList());
        return currentClientAccounts;
    }

    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> createAccount(Authentication authentication) {
        Client client = clientService.findByEmail(authentication.getName());

        if (client.getAccounts().size()>=3){
            return new ResponseEntity<>("Ya tienes el máximo de cuentas permitidas", HttpStatus.FORBIDDEN);
        }

        Account account = new Account(accountService.getNewRandomAccountNumber(), LocalDateTime.now(), 0);
        client.addAccount(account);
        accountService.save(account);
        return new ResponseEntity<>("Cuenta creada", HttpStatus.CREATED);

    }



}
