package com.project1.demo.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.project1.demo.model.Loan;
import com.project1.demo.model.Offer;

//Komponent?

@Component
public class LoanProcessor {

    public void linkLoansToOffer(List<Loan> loans, Offer offer) {
        for (Loan loan : loans) {
            loan.setOffer(offer);
        }
    }

    public void syncLoans(List<Loan> newLoans, List<Loan> existingLoans, Offer offer) {
        existingLoans.removeIf(existingLoan -> !newLoans.contains(existingLoan));

        for (Loan newLoan : newLoans) {
            if (!existingLoans.contains(newLoan)) {
                newLoan.setOffer(offer);
                existingLoans.add(newLoan);
            }
        }
    }
}