package com.keyin.service;

import com.keyin.model.MCard;
import com.keyin.model.Ride;
import com.keyin.model.TopUp;
import com.keyin.repository.MCardRepository;
import com.keyin.repository.RideRepository;
import com.keyin.repository.TopUpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MCardService {
    @Autowired
    private MCardRepository mCardRepository;

    @Autowired
    private TopUpRepository topUpRepository;

    @Autowired
    private RideRepository rideRepository;

    public MCard issueNewmCard(String cardNumber) {
        if (cardNumber == null || cardNumber.isEmpty()) {
            throw new IllegalArgumentException("Card number cannot be null or empty");
        }

        if (mCardRepository.existsByCardNumber(cardNumber)) {
            throw new RuntimeException("Card with this number already exists");
        }

        MCard card = new MCard();
        card.setCardNumber(cardNumber);
        card.setBalance(0.00);
        return mCardRepository.save(card);
    }

    public List<MCard> getAllMCards() {
        return mCardRepository.findAll();
    }

    public Optional<MCard> getMCard(Long id) {
        return mCardRepository.findById(id);
    }

    @Transactional
    public MCard topUpmCard(String cardNumber, double amount) {
        MCard card = mCardRepository.findByCardNumber(cardNumber)
                .orElseThrow(() -> new IllegalArgumentException("MCard not found"));

        if (amount <= 0) {
            throw new IllegalArgumentException("Top-up amount must be greater than zero");
        }

        card.setBalance(card.getBalance() + amount);

        TopUp topUp = new TopUp();
        topUp.setMCard(card);
        topUp.setAmount(amount);
        topUpRepository.save(topUp);

        return mCardRepository.save(card);
    }

    @Transactional
    public MCard payForRide(String cardNumber, double fare) {
        MCard card = mCardRepository.findByCardNumber(cardNumber)
                .orElseThrow(() -> new IllegalArgumentException("MCard not found"));
        if (card.getBalance() < fare) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        if (fare <= 0) {
            throw new IllegalArgumentException("Fare must be greater than zero");
        }

        card.setBalance(card.getBalance() - fare);

        Ride ride = new Ride();
        ride.setMCard(card);
        ride.setFare(fare);
        rideRepository.save(ride);

        return mCardRepository.save(card);
    }

    public List<Ride> getmCardRideHistory(String cardNumber) {
        MCard card = mCardRepository.findByCardNumber(cardNumber)
                .orElseThrow(() -> new IllegalArgumentException("MCard not found"));
        return rideRepository.findByMCard(card);
    }

    public double checkBalance(String cardNumber) {
        MCard card = mCardRepository.findByCardNumber(cardNumber)
                .orElseThrow(() -> new IllegalArgumentException("MCard not found"));
        return card.getBalance();
    }
}
