package com.project1.demo.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.project1.demo.model.Offer;
import com.project1.demo.model.enums.OfferStatus;

import com.project1.demo.repo.OfferRepo;

@RestController
@RequestMapping("/stats")
public class StatsController {
    
    //Använda Jpa query istället för stream
    //Bryta ut till Service Folder

    @Autowired
    private OfferRepo offerRepo;

    @GetMapping("/conversion")
    public ResponseEntity<?> getConversionStats( @RequestParam(defaultValue = "30") int days) {
        
        long totalOffers = offerRepo.count();
        if (totalOffers == 0) {
            return ResponseEntity.ok("Inga offerter har skapats ännu.");
        }

        LocalDate conversionPeriod = LocalDate.now().minusDays(days);

        long acceptedInPeriod = offerRepo.findAll().stream()
            .filter(o -> o.getStatus() == OfferStatus.ACCEPTED)
            .filter(o -> o.getCreationDate() != null && o.getCreationDate().isAfter(conversionPeriod))
            .count();

        double conversionRate = (double) acceptedInPeriod / totalOffers * 100;

        Map<String, Object> response = new HashMap<>();
        response.put("totalOffers", totalOffers);
        response.put("acceptedWithinDays", acceptedInPeriod);
        response.put("conversionRate", String.format("%.2f", conversionRate) + " %");

        return ResponseEntity.ok(response);

    }

}
