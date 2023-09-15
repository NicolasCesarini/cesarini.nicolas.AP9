package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImplement implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public void save(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    @Override
    public void deleteAllByUsedAccount(Account account) {
        transactionRepository.deleteAllByUsedAccount(account);
    }

    @Override
    public List<Transaction> findAllByUsedAccount(Account account) {
        return transactionRepository.findAllByUsedAccount(account);
    }

    @Override
    public void deleteAll(List<Transaction> transactions) {
        transactionRepository.deleteAll(transactions);
    }
}
