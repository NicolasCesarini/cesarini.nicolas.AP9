package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.Loan;

import java.util.List;

public interface LoanService {
    boolean existsById(Long id);
    Loan findById(Long id);
    List<LoanDTO> getLoansDTO();
    void save (Loan loan);
}
