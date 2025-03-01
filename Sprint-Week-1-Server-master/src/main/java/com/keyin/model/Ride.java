package com.keyin.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Ride {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double fare;
    private LocalDateTime rideAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "mcard_id", nullable = false)
    private MCard MCard;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getFare() {
        return fare;
    }

    public void setFare(double fare) {
        this.fare = fare;
    }

    public LocalDateTime getRideAt() {
        return rideAt;
    }

    public void setRideAt(LocalDateTime rideAt) {
        this.rideAt = rideAt;
    }

    public MCard getMCard() {
        return MCard;
    }

    public void setMCard(MCard mCard) {
        MCard = mCard;
    }
}
