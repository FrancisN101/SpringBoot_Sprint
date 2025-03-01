package com.keyin.controller;

import com.keyin.model.MCard;
import com.keyin.model.Ride;
import com.keyin.service.MCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mcard")
public class MCardController {
    @Autowired
    private MCardService mCardService;

    @PostMapping("/issue")
    public ResponseEntity<MCard> issueNewMCard(@RequestParam String cardNumber) {
        return ResponseEntity.ok(mCardService.issueNewmCard(cardNumber));
    }

    @GetMapping("/all")
    public List<MCard> getAllMCards() {
        return mCardService.getAllMCards();
    }

    @PostMapping("/topup/{cardNumber}")
    public ResponseEntity<MCard> topUpMCard(@PathVariable String cardNumber, @RequestParam double amount) {
        return ResponseEntity.ok(mCardService.topUpmCard(cardNumber, amount));
    }

    @PostMapping("/pay/{cardNumber}")
    public ResponseEntity<MCard> payForRide(@PathVariable String cardNumber, @RequestParam double fare) {
        return ResponseEntity.ok(mCardService.payForRide(cardNumber, fare));
    }

    @GetMapping("/balance/{cardNumber}")
    public ResponseEntity<Double> checkBalance(@PathVariable String cardNumber) {
        return ResponseEntity.ok(mCardService.checkBalance(cardNumber));
    }

    @GetMapping("/history/{cardNumber}")
    public ResponseEntity<List<Ride>> getMCardRideHistory(@PathVariable String cardNumber) {
        return ResponseEntity.ok(mCardService.getmCardRideHistory(cardNumber));
    }
}
