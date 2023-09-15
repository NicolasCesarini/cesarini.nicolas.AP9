package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.utils.AccountUtils;

import java.util.List;

public interface AccountService {
    List<AccountDTO> GetAccountsDTO();
    Account findByIdAndOwner(Long id, Client client);
    void save(Account account);
    boolean existsByNumber(String accountNumber);
    Account findByNumber(String accountNumber);
    Account findByNumberAndOwner(String accountNumber, Client client);
    boolean existsByNumberAndOwner(String accountNumber, Client client);
    String getNewRandomAccountNumber();
    void deleteById (Long id);
}
