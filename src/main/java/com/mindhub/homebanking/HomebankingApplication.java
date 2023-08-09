package com.mindhub.homebanking;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.time.LocalDate;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository){
		return (args -> {
			Client client1 = new Client("Melba", "Morel", "melba@mindhub.com");
			Client client2 = new Client("Nicolas", "Cesarini", "nicolas@mindhub.com");

			clientRepository.save(client1);
			clientRepository.save(client2);

			LocalDate today =  LocalDate.now();
			LocalDate tomorrow = today.plusDays(1);

			Account account1 = new Account("VIN001", today, 5000);
			Account account2 = new Account("VIN002", tomorrow, 7500);
			Account account3 = new Account("VIN003", today, 10000);
			Account account4 = new Account("VIN004", tomorrow, 15000);

			client1.addAccount(account1);
			client1.addAccount(account2);

			ClientDTO clientDTO = new ClientDTO(client1);

			accountRepository.save(account1);
			accountRepository.save(account2);
			client2.addAccount(account3);
			client2.addAccount(account4);
			accountRepository.save(account3);
			accountRepository.save(account4);

			Transaction transaction1 = new Transaction(TransactionType.DEBIT,1500,"Débito",today);
			Transaction transaction2 = new Transaction(TransactionType.CREDIT,2000,"Crédito",today);
			Transaction transaction3 = new Transaction(TransactionType.DEBIT,2500,"Débito",today);
			Transaction transaction4 = new Transaction(TransactionType.CREDIT,1000,"Crédito",today);
			Transaction transaction5 = new Transaction(TransactionType.DEBIT,1500,"Débito",today);
			Transaction transaction6 = new Transaction(TransactionType.CREDIT,2000,"Crédito",today);
			Transaction transaction7 = new Transaction(TransactionType.DEBIT,2500,"Débito",today);
			Transaction transaction8 = new Transaction(TransactionType.CREDIT,1000,"Crédito",today);

			account1.addTransaction(transaction1);
			account1.addTransaction(transaction2);
			account2.addTransaction(transaction3);
			account2.addTransaction(transaction4);
			account3.addTransaction(transaction5);
			account3.addTransaction(transaction6);
			account4.addTransaction(transaction7);
			account4.addTransaction(transaction8);

			transactionRepository.save(transaction1);
			transactionRepository.save(transaction2);
			transactionRepository.save(transaction3);
			transactionRepository.save(transaction4);
			transactionRepository.save(transaction5);
			transactionRepository.save(transaction6);
			transactionRepository.save(transaction7);
			transactionRepository.save(transaction8);
		});
	}
}
