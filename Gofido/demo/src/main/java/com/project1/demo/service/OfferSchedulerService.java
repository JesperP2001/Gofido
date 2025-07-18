package com.project1.demo.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project1.demo.model.Offer;
import com.project1.demo.model.enums.OfferStatus;
import com.project1.demo.repo.OfferRepo;

@Service
public class OfferSchedulerService {
    @Autowired
    private OfferRepo offerRepo;

    @Scheduled(cron = "0 0 0 * * ?")//varje dag midnatt
    @Transactional
    public void expireOffers() {
        LocalDate today = LocalDate.now();

        // Hämta alla offer som är inte expired eller Accpeted och som har expirationDate före idag
        List<Offer> expiredOffers = offerRepo.findByStatusNotInAndExpirationDateBefore(
            List.of(OfferStatus.EXPIRED, OfferStatus.ACCEPTED), today);

        for (Offer offer : expiredOffers) {
            offer.setStatus(OfferStatus.EXPIRED);
            offer.setPersonalNumber(null);
            offerRepo.save(offer);
        }

        System.out.println("Expired offers updated: " + expiredOffers.size());
    }
}

