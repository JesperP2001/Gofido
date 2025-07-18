package com.project1.demo.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project1.demo.model.Offer;
import com.project1.demo.model.enums.OfferStatus;

@Repository
public interface OfferRepo extends JpaRepository<Offer, Long>{
    List<Offer> findByStatusNotInAndExpirationDateBefore(List<OfferStatus> status, LocalDate date);
}
