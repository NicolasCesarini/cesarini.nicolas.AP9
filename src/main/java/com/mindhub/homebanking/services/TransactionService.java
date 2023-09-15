package com.mindhub.homebanking.services;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Transaction;

import java.util.List;

public interface TransactionService {
    void save(Transaction transaction);
    void deleteAllByUsedAccount (Account account);
    List<Transaction> findAllByUsedAccount (Account account);
    void deleteAll (List<Transaction> transactions);
}
