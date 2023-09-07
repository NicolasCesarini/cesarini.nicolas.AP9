package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    private AccountController accountController;

    @Autowired
    private CardController cardController;

    @Autowired
    private ClientService clientService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AccountService accountService;

    @Transactional
    @PostMapping("/transactions")
    public ResponseEntity<Object> makeATransaction(

            @RequestParam String fromAccountNumber, @RequestParam String toAccountNumber,
            @RequestParam double amount, @RequestParam String description,
            Authentication authentication) {

        if (description.isBlank()) {
            return new ResponseEntity<>("La descripción no fue ingresada", HttpStatus.FORBIDDEN);
        }

        if(amount == 0) {
            return new ResponseEntity<>("El monto no fue ingresado", HttpStatus.FORBIDDEN);
        }

        if (fromAccountNumber.equals("VIN")){
            return new ResponseEntity<>("El número de cuenta de salida no fue ingresado", HttpStatus.FORBIDDEN);
        }

        if (toAccountNumber.equals("VIN")){
            return new ResponseEntity<>("El número de cuenta de origen no fue ingresado", HttpStatus.FORBIDDEN);
        }

        Client debitClient = clientService.findByEmail(authentication.getName());
        //Client creditClient = accountRepository.findByNumber(toAccountNumber).getOwner();

        if (accountService.findByNumber(fromAccountNumber) == null){
            return new ResponseEntity<>("La cuenta de origen ingresada no exite", HttpStatus.FORBIDDEN);
        }

        if (accountService.findByNumber(toAccountNumber) == null){
            return new ResponseEntity<>("La cuenta de destino ingresada no exite", HttpStatus.FORBIDDEN);
        }

        if (accountService.findByNumberAndOwner(fromAccountNumber, debitClient) == null){
            return new ResponseEntity<>("La cuenta de origen ingresada no pertenece al usuario autenticado", HttpStatus.FORBIDDEN);
        }

        if (fromAccountNumber.equals(toAccountNumber)) {
            return new ResponseEntity<>("La cuenta de origen y destino coinciden", HttpStatus.FORBIDDEN);
        }

        if(accountService.findByNumberAndOwner(fromAccountNumber, debitClient).getBalance() < amount) {
            return new ResponseEntity<>("Saldo insuficiente", HttpStatus.FORBIDDEN);
        }

        if(amount < 0) {
            return new ResponseEntity<>("El monto de la transferencia debe ser mayor a 0", HttpStatus.FORBIDDEN);
        }

        Transaction debitTransaction = new Transaction(TransactionType.DEBIT, amount, description, LocalDateTime.now());
        Transaction creditTransaction = new Transaction(TransactionType.CREDIT, amount, description, LocalDateTime.now());

        Account debitAccount = accountService.findByNumber(fromAccountNumber);
        debitAccount.addTransaction(debitTransaction);
        transactionService.save(debitTransaction);
        double debitAccountBalance = debitAccount.getBalance();
        debitAccount.setBalance(debitAccountBalance-amount);
        accountService.save(debitAccount);

        Account creditAccount = accountService.findByNumber(toAccountNumber);
        creditAccount.addTransaction(creditTransaction);
        transactionService.save(creditTransaction);
        double creditAccountBalance = creditAccount.getBalance();
        creditAccount.setBalance(creditAccountBalance + amount);
        accountService.save(creditAccount);

        return new ResponseEntity<>("La transacción se realizó con exito", HttpStatus.ACCEPTED);
    }

}
