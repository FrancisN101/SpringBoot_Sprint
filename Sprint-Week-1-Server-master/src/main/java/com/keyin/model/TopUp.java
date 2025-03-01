package com.keyin.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class TopUp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double amount;
    private LocalDateTime topUpAt = LocalDateTime.now();

    @ManyToOne
    private MCard MCard;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getTopUpAt() {
        return topUpAt;
    }

    public void setTopUpAt(LocalDateTime topUpAt) {
        this.topUpAt = topUpAt;
    }

    public MCard getMCard() {
        return MCard;
    }

    public void setMCard(MCard mCard) {
        MCard = mCard;
    }
}

