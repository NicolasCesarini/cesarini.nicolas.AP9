package com.mindhub.homebanking.services.implement;


import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardServiceImplement implements CardService {
    @Autowired
    private CardRepository cardRepository;

    @Override
    public void save(Card card) {
        cardRepository.save(card);
    }

    @Override
    public boolean existsCardByNumber(String cardNumber) {
        return cardRepository.existsByNumber(cardNumber);
    }

    @Override
    public boolean existsCardByCvv(Integer cardCvv) {
        return cardRepository.existsByCvv(cardCvv);
    }

    @Override
    public void deleteById(Long id) {
        cardRepository.deleteById(id);
    }
}