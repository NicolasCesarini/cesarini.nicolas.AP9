package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;

import java.util.HashSet;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

public class ClientDTO {

    private long id;
    private String firstName;
    private String lastName;
    private String email;

    private Set<AccountDTO> accounts;

    private Set<ClientLoanDTO> loans;

    private Set<CardDTO> cards;

    public ClientDTO(Client client) {
        this.id = client.getId();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.email = client.getEmail();
        this.accounts = client.getAccounts()
                        .stream()
                        .map(currentAccount -> new AccountDTO(currentAccount))
                        .collect(toSet());

        this.loans = client.getClientLoans()
                .stream()
                .map(currentLoan -> new ClientLoanDTO(currentLoan))
                .collect(toSet());

        this.cards = client.getCards()
                .stream()
                .map(card -> new CardDTO(card))
                .collect(toSet());
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Set<AccountDTO> getAccounts() {
        return accounts;
    }

    public Set<ClientLoanDTO> getLoans() {
        return loans;
    }

    public Set<CardDTO> getCards() {
        return cards;
    }
}
