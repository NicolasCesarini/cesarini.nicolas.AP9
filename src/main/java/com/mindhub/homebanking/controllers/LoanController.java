package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientLoanService clientLoanService;

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/loans")
    public List<LoanDTO> getLoans() {
        return loanService.getLoansDTO();
    }

    @Transactional
    @PostMapping("/loans")
    public ResponseEntity<Object> makeLoan(@RequestBody LoanApplicationDTO loanApplicationDTO,
                                           Authentication authentication) {

        //403 forbidden, si alguno de los datos no es
        //válido

        if (loanApplicationDTO.getLoanId() == 0) {
            return new ResponseEntity<>("El tipo de prestamo no fue seleccionado", HttpStatus.FORBIDDEN);
        }

        if (loanApplicationDTO.getPayments() == 0) {
            return new ResponseEntity<>("La cantidad de cuotas no fue seleccionada", HttpStatus.FORBIDDEN);
        }

        if (loanApplicationDTO.getToAccountNumber().isBlank()) {
            return new ResponseEntity<>("La cuenta de destino no fue seleccionada", HttpStatus.FORBIDDEN);
        }

        if(loanApplicationDTO.getAmount() == 0) {
            return new ResponseEntity<>("El monto soliciado no fue ingresado", HttpStatus.FORBIDDEN);
        }
        //403 forbidden, si la cuenta de destino no existe
        Account account = accountService.findByNumber(loanApplicationDTO.getToAccountNumber());

        if (account == null) {
            return new ResponseEntity<>("La cuenta de destino no existe", HttpStatus.FORBIDDEN);
        }

        //403 forbidden, si la cuenta de destino no
        //pertenece al cliente autenticado

        Client client = clientService.findByEmail(authentication.getName());
        if (!accountService.existsByNumberAndOwner(account.getNumber(), client)) {
            return new ResponseEntity<>("La cuenta de destino no pertenece al cliente autenticado",
                                        HttpStatus.FORBIDDEN);
        }

        // 403 forbidden, si el préstamo no existe
        if(!loanService.existsById(loanApplicationDTO.getLoanId())) {
            return new ResponseEntity<>("El prestamo seleccionado no existe", HttpStatus.FORBIDDEN);
        }

        //403 forbidden, si el monto solicitado supera el
        //monto máximo permitido del préstamo
        //solicitado

        Loan loan = loanService.findById(loanApplicationDTO.getLoanId());

        if (loanApplicationDTO.getAmount() > loan.getMaxAmount()) {
            return new ResponseEntity<>("El monto solocitado es mayor al monto disponible para el prestamo de " +
                    "tipo " + loan.getName(), HttpStatus.FORBIDDEN);
        }

        //403 forbidden, si la cantidad de cuotas no está
        //disponible para el préstamo solicitado

        if (!loan.getPayments().contains(loanApplicationDTO.getPayments())) {
            return new ResponseEntity<>("La cantidad de cuotas no esta diponible para el prestamo de tipo " +
                    loan.getName(), HttpStatus.FORBIDDEN);
        }

        //Se debe crear una solicitud de préstamo con el monto solicitado sumando el 20% del mismo



        ClientLoan makeLoan = new ClientLoan(loanApplicationDTO.getAmount() * 1.2, loanApplicationDTO.getPayments());


        client.addClientLoan(makeLoan);
        loan.addClientLoan(makeLoan);
        clientLoanService.save(makeLoan);

        //Se debe crear una transacción “CREDIT” asociada a la cuenta de destino (el monto debe quedar positivo) con la descripción concatenando el nombre del préstamo y la frase “loan approved”

        Transaction loanTransaction = new Transaction(TransactionType.CREDIT, loanApplicationDTO.getAmount(),
                loan.getName()
                        + " loan approved", LocalDateTime.now());

        account.addTransaction(loanTransaction);
        transactionService.save(loanTransaction);



        //Se debe actualizar la cuenta de destino sumando el monto solicitado.

        double accountBalance = account.getBalance();
        account.setBalance(accountBalance + loanApplicationDTO.getAmount());
        accountService.save(account);



        return new ResponseEntity<>("Todo ok", HttpStatus.ACCEPTED);

    }

}
