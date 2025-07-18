package com.project1.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.project1.demo.model.Offer;
import com.project1.demo.model.enums.OfferStatus;
import com.project1.demo.repo.OfferRepo;


public class OfferSchedularServiceTest {

@Mock
private OfferRepo offerRepo;  // Mockad repository

@InjectMocks
private OfferSchedulerService schedulerService;

@BeforeEach
void setUp() {
    MockitoAnnotations.openMocks(this); // Aktiverar mockningen
}

@Test
void expireOffers_shouldOnlyExpireValidOffers() {

    System.out.println("TESTET KÖRS ✅");
    // Arrange
    LocalDate today = LocalDate.now();

    Offer offer1 = new Offer();
    offer1.setId(1L);
    offer1.setStatus(OfferStatus.CREATED);
    offer1.setExpirationDate(today.minusDays(1));
    offer1.setPersonalNumber("123456-7890");

    Offer offer2 = new Offer();
    offer2.setId(2L);
    offer2.setStatus(OfferStatus.ACCEPTED);
    offer2.setExpirationDate(today.minusDays(2));
    offer2.setPersonalNumber("987654-3210");

    Offer offer3 = new Offer();
    offer3.setId(3L);
    offer3.setStatus(OfferStatus.UPDATED);
    offer3.setExpirationDate(today.minusDays(3));
    offer3.setPersonalNumber("111111-1111");

    //  offer1 offer3 (offer2 is ACCEPTED)
    List<Offer> expiredCandidates = List.of(offer1, offer3);

    when(offerRepo.findByStatusNotInAndExpirationDateBefore(
        List.of(OfferStatus.EXPIRED, OfferStatus.ACCEPTED),
        today)
    ).thenReturn(expiredCandidates);

    
    schedulerService.expireOffers();

    ArgumentCaptor<Offer> captor = ArgumentCaptor.forClass(Offer.class);
    verify(offerRepo, times(2)).save(captor.capture());

    List<Offer> saved = captor.getAllValues();

    System.out.println("Följande offer sattes till EXPIRED:");
    for (Offer o : saved) {
    System.out.println("Offer ID: " + o.getId() + ", tidigare status: " + o.getStatus());
}

    for (Offer o : saved) {
        assertEquals(OfferStatus.EXPIRED, o.getStatus());
        assertNull(o.getPersonalNumber());
    }

    verify(offerRepo, never()).save(argThat(o -> o.getId() == 2L));
}
}
