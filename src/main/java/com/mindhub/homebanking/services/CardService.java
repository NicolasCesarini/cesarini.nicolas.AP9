package com.mindhub.homebanking.services;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.utils.CardUtils;

public interface CardService {
    void save (Card card);
    boolean existsCardByNumber (String cardNumber);
    boolean existsCardByCvv (Integer cardCvv);
    void deleteById (Long id);
    String getNewRandomCardNumber ();
    int getNewRandomCardCvv ();
}