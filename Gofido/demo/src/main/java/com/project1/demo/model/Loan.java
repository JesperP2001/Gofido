package com.project1.demo.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Loan {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

private String bank;

private BigDecimal loanAmount;

@ManyToOne
@JoinColumn(name = "offer_id")
@JsonIgnore  // Förhindrar att hela offer-objektet skickas med i JSON
private Offer offer;

public Long getOfferId() {
    return offer != null ? offer.getId() : null;
}

// Constructors
public Loan() {
}

public Loan(String bank, BigDecimal loanAmount) {
    this.bank = bank;
    this.loanAmount = loanAmount;
}

// Getters and Setters

public Long getId() {
  return id;
}

public String getBank() {
    return bank;
}

public void setBank(String bank) {
    this.bank = bank;
}

public BigDecimal getLoanAmount() {
  return loanAmount;
}

public void setLoanAmount(BigDecimal loanAmount) {
  this.loanAmount = loanAmount;
}

public Offer getOffer() {
    return offer;
}

public void setOffer(Offer offer) {
    this.offer = offer;
}


    
}
