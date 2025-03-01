package com.keyin.repository;

import com.keyin.model.MCard;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MCardRepository extends JpaRepository<MCard, Long> {
    Optional<MCard> findByCardNumber(String cardNumber);
    boolean existsByCardNumber(String cardNumber);
}
