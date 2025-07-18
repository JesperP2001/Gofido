package com.project1.demo.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project1.demo.model.Offer;
import com.project1.demo.model.enums.OfferStatus;
import com.project1.demo.repo.OfferRepo;

@Service
public class OfferService {

    @Autowired private OfferRepo offerRepo;
    @Autowired private PremiumCalculator premiumCalculator;
    @Autowired private LoanProcessor loanProcessor;

    public Offer createOffer(Offer offer) {
        if (offer.getLoans() != null) {
            loanProcessor.linkLoansToOffer(offer.getLoans(), offer);
        }

        offer.setPremium(premiumCalculator.calculatePremium(offer.getLoans()));
        offer.setCreationDate(LocalDate.now());
        offer.setExpirationDate(LocalDate.now().plusDays(30));
        offer.setStatus(OfferStatus.CREATED);

        return offerRepo.save(offer);
    }

    public Optional<Offer> updateOffer(Long id, Offer updatedOffer) {
        return offerRepo.findById(id).map(existingOffer -> {
            loanProcessor.syncLoans(updatedOffer.getLoans(), existingOffer.getLoans(), existingOffer);

            existingOffer.setPersonalNumber(updatedOffer.getPersonalNumber());
            existingOffer.setPremium(premiumCalculator.calculatePremium(updatedOffer.getLoans()));
            existingOffer.setStatus(OfferStatus.UPDATED);

            return offerRepo.save(existingOffer);
        });
    }

    public Optional<Offer> acceptOffer(Long id) {
        return offerRepo.findById(id).map(offer -> {
            if (offer.getStatus() == OfferStatus.ACCEPTED) return null;

            offer.setStatus(OfferStatus.ACCEPTED);
            offer.setExpirationDate(null);

            return offerRepo.save(offer);
        });
    }
}
