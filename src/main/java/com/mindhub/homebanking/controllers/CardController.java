package com.mindhub.homebanking.controllers;


import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.services.CardService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    private CardService cardService;

    @Autowired
    private ClientService clientService;

    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> createCard(@RequestParam CardType cardType,
                                             @RequestParam CardColor cardColor,
                                             Authentication authentication) {
        Client client = clientService.findByEmail(authentication.getName());

        Set<Card> cards = client.getCards();

        if (!cards.stream()
                .filter(card -> card.getType().equals(cardType))
                .filter(card -> card.getColor().equals(cardColor))
                .collect(toList()).isEmpty()){
            return new ResponseEntity<>("Ya tiene una tarjeta de este tipo", HttpStatus.FORBIDDEN);
        }

        Card card = new Card(client.getFirstName()+" "+client.getLastName(),
                cardType, cardColor, getCardNumber(), getCardCvv(), LocalDateTime.now(), LocalDateTime.now().plusYears(5));

        client.addCard(card);
        cardService.save(card);
        return new ResponseEntity<>("Tarjeta creada", HttpStatus.CREATED);

    }

    @RequestMapping("/clients/current/cards")
    public List<CardDTO> getCurrentCards(Authentication authentication) {
        Client client = clientService.findByEmail(authentication.getName());
        List<CardDTO> currentClientCards = client.getCards().stream()
                .map(card -> new CardDTO(card)).collect(toList());
        return currentClientCards;
    }

    @DeleteMapping("/clients/current/cards/{id}")
    public void deleteCard(@PathVariable Long id) {
        cardService.deleteById(id);
    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public String getCardNumber() {
        String numberCard;
        do {
            numberCard = String.format("%04d", getRandomNumber(1, 9999)) + "-" + String.format("%04d", getRandomNumber(1, 9999)) + "-" + String.format("%04d", getRandomNumber(1, 9999)) + "-" + String.format("%04d", getRandomNumber(1, 9999));
        } while (cardService.existsCardByNumber(numberCard));
        return numberCard;
    }

    public int getCardCvv() {
        int cardCvv;
        do {
            cardCvv = getRandomNumber(100, 999);
        } while (cardService.existsCardByCvv(cardCvv));
        return cardCvv;
    }

}
