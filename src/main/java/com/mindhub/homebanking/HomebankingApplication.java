package com.mindhub.homebanking;

import com.mindhub.homebanking.controllers.AccountController;
import com.mindhub.homebanking.controllers.CardController;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import com.mindhub.homebanking.services.*;
import com.mindhub.homebanking.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	/*@Autowired
	private PasswordEncoder passwordEncoder;

	@Bean
	public CommandLineRunner initData(ClientService clientService, AccountService accountService,
									  TransactionService transactionService, LoanService loanService,
									  ClientLoanService clientLoanService, CardService cardService){
		return (args -> {
			Client client1 = new Client("Melba", "Morel", "melba@mindhub.com", passwordEncoder.encode("123"));
			Client client2 = new Client("Nicolas", "Cesarini", "nicolas@mindhub.com", passwordEncoder.encode("123"));
			Client client3 = new Client("admin","admin","admin@mindhub.com", passwordEncoder.encode("123"));

			clientService.save(client1);
			clientService.save(client2);
			clientService.save(client3);

			LocalDateTime today =  LocalDateTime.now();
			LocalDateTime tomorrow = today.plusDays(1);

			Account account1 = new Account(accountService.getNewRandomAccountNumber(), today, 5000);
			Account account2 = new Account(accountService.getNewRandomAccountNumber(), tomorrow, 7500);
			Account account3 = new Account(accountService.getNewRandomAccountNumber(), today, 10000);
			Account account4 = new Account(accountService.getNewRandomAccountNumber(), tomorrow, 15000);

			client1.addAccount(account1);
			client1.addAccount(account2);

			ClientDTO clientDTO = new ClientDTO(client1);

			accountService.save(account1);
			accountService.save(account2);
			client2.addAccount(account3);
			client2.addAccount(account4);
			accountService.save(account3);
			accountService.save(account4);

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

			transactionService.save(transaction1);
			transactionService.save(transaction2);
			transactionService.save(transaction3);
			transactionService.save(transaction4);
			transactionService.save(transaction5);
			transactionService.save(transaction6);
			transactionService.save(transaction7);
			transactionService.save(transaction8);

			Loan loan1 = new Loan("Hipotecario", 500000, List.of(12, 24, 36, 48, 60));
			Loan loan2 = new Loan("Personal", 100000, List.of(6, 12, 24, 36));
			Loan loan3 = new Loan("Automotriz", 300000, List.of(6, 12, 24, 36));

			loanService.save(loan1);
			loanService.save(loan2);
			loanService.save(loan3);

			ClientLoan clientLoan1 = new ClientLoan(400000,60);
			ClientLoan clientLoan2 = new ClientLoan(50000,12);
			ClientLoan clientLoan3 = new ClientLoan(100000,24);
			ClientLoan clientLoan4 = new ClientLoan(200000,36);
			client1.addClientLoan(clientLoan1);
			client1.addClientLoan(clientLoan2);
			client2.addClientLoan(clientLoan3);
			client2.addClientLoan(clientLoan4);
			loan1.addClientLoan(clientLoan1);
			loan2.addClientLoan(clientLoan2);
			loan2.addClientLoan(clientLoan3);
			loan3.addClientLoan(clientLoan4);
			clientLoanService.save(clientLoan1);
			clientLoanService.save(clientLoan2);
			clientLoanService.save(clientLoan3);
			clientLoanService.save(clientLoan4);

			Card card1 = new Card(client1.getFirstName() + " " + client1.getLastName(), CardType.DEBIT, CardColor.GOLD,
					cardService.getNewRandomCardNumber(), cardService.getNewRandomCardCvv(), LocalDateTime.now(),
					LocalDateTime.now().plusYears(5));
			Card card2 = new Card(client1.getFirstName() + " " + client1.getLastName(), CardType.CREDIT, CardColor.TITANIUM,
					cardService.getNewRandomCardNumber(), cardService.getNewRandomCardCvv(), LocalDateTime.now(),
					LocalDateTime.now().plusYears(5));
			Card card3 = new Card(client2.getFirstName() + " " + client2.getLastName(), CardType.CREDIT, CardColor.SILVER,
					cardService.getNewRandomCardNumber(), cardService.getNewRandomCardCvv(), LocalDateTime.now(),
					LocalDateTime.now().plusYears(5));
			client1.addCard(card1);
			client1.addCard(card2);
			client2.addCard(card3);
			cardService.save(card1);
			cardService.save(card2);
			cardService.save(card3);

		});
	}*/
}
