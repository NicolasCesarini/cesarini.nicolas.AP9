package com.mindhub.homebanking;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
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
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository){
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
		});
	}
}
