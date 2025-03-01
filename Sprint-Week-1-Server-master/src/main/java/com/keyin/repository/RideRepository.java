package com.keyin.repository;

import com.keyin.model.MCard;
import com.keyin.model.Ride;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RideRepository extends JpaRepository<Ride, Long> {
    List<Ride> findByMCard(MCard mCard);
}
