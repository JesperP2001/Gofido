package com.project1.demo.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Component;

import com.project1.demo.model.Loan;

@Component
public class PremiumCalculator {

    private static final BigDecimal PREMIUM_RATE = BigDecimal.valueOf(0.038);

    public BigDecimal calculatePremium(List<Loan> loans) {
        return loans.stream()
                .map(Loan::getLoanAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .multiply(PREMIUM_RATE);
    }
}