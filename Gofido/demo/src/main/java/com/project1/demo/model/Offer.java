package com.project1.demo.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.project1.demo.model.enums.OfferStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Offer {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private String personalNumber;

    private BigDecimal monthlyPayment;

    private BigDecimal premium;

    private LocalDate creationDate;

    private LocalDate expirationDate;

    @Enumerated(EnumType.STRING)
    private OfferStatus status;
    
    @OneToMany( mappedBy = "offer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Loan> loans;

   
}

