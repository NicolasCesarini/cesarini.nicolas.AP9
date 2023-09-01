package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class LoanController {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/loans")
    public List<LoanDTO> getLoans() {
        List<LoanDTO> allLoans = loanRepository.findAll().stream()
                                                .map(loan -> new LoanDTO(loan))
                                                .collect(toList());

        return allLoans;
    }

    @Transactional
    @PostMapping("/loans")
    public ResponseEntity<Object> makeLoan(@RequestBody LoanApplicationDTO loanApplicationDTO,
                                           Authentication authentication) {
        //solicitud de
        //préstamo creado al
        //cliente autenticado
        //transacción creada
        //para cuenta de
        //destino
        //cuenta de destino
        //actualizada con el
        //monto



        //éxito: 201 created
        //respuestas de error:
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
        Account account = accountRepository.findByNumber(loanApplicationDTO.getToAccountNumber());

        if (account == null) {
            return new ResponseEntity<>("La cuenta de destino no existe", HttpStatus.FORBIDDEN);
        }

        //403 forbidden, si la cuenta de destino no
        //pertenece al cliente autenticado

        Client client = clientRepository.findByEmail(authentication.getName());
        if (!accountRepository.existsByNumberAndOwner(account.getNumber(), client)) {
            return new ResponseEntity<>("La cuenta de destino no pertenece al cliente autenticado",
                                        HttpStatus.FORBIDDEN);
        }

        // 403 forbidden, si el préstamo no existe
        if(!loanRepository.existsById(loanApplicationDTO.getLoanId())) {
            return new ResponseEntity<>("El prestamo seleccionado no existe", HttpStatus.FORBIDDEN);
        }

        //403 forbidden, si el monto solicitado supera el
        //monto máximo permitido del préstamo
        //solicitado
        //if (loanRepository.findById(loanApplicationDTO.getLoanId()))

        //403 forbidden, si la cantidad de cuotas no está
        //disponible para el préstamo solicitado
        return new ResponseEntity<>("Todo ok", HttpStatus.ACCEPTED);

    }

}
