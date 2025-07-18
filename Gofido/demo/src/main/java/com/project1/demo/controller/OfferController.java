package com.project1.demo.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project1.demo.model.Offer;

import com.project1.demo.service.OfferService;

import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/offer") 
public class OfferController {

    @Autowired
    private OfferService offerService;

  @PostMapping("/addOffer")
    public ResponseEntity<Offer> addOffer(@RequestBody Offer offer) {
    Offer savedOffer = offerService.createOffer(offer);
    return new ResponseEntity<>(savedOffer, HttpStatus.CREATED);
    } 

    @PutMapping("/{id}")
    public ResponseEntity<Offer> updateOffer(@PathVariable Long id, @RequestBody Offer updatedOffer) {
        return offerService.updateOffer(id, updatedOffer)
            .map(ResponseEntity::ok)
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}/accept")
    public ResponseEntity<?> acceptOffer(@PathVariable Long id) {
        var optionalOffer = offerService.acceptOffer(id);
        if (optionalOffer.isEmpty()) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Offerten kunde inte accepteras (kan vara redan accepterad eller saknas).");
        }
        return ResponseEntity.ok(optionalOffer.get());
    }

}
