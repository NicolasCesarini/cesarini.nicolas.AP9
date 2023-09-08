package com.mindhub.homebanking.services;

import com.mindhub.homebanking.models.Card;

public interface CardService {
    void save (Card card);
    boolean existsCardByNumber (String cardNumber);
    boolean existsCardByCvv (Integer cardCvv);
    void deleteById (Long id);
}