package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class AccountServiceImplement implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public List<AccountDTO> GetAccountsDTO() {
        return accountRepository.findAll().stream()
                .map(currentAccount -> new AccountDTO(currentAccount))
                .collect(toList());
    }

    @Override
    public Account findByIdAndOwner(Long id, Client client) {
        return accountRepository.findByIdAndOwner(id, client);
    }

    @Override
    public void save(Account account) {
        accountRepository.save(account);
    }

    @Override
    public boolean existsByNumber(String accountNumber) {
        return accountRepository.existsByNumber(accountNumber);
    }

    @Override
    public Account findByNumber(String accountNumber) {
        return accountRepository.findByNumber(accountNumber);
    }

    @Override
    public Account findByNumberAndOwner(String accountNumber, Client client) {
        return accountRepository.findByNumberAndOwner(accountNumber, client);
    }

    @Override
    public boolean existsByNumberAndOwner(String accountNumber, Client client) {
        return accountRepository.existsByNumberAndOwner(accountNumber, client);
    }
}
