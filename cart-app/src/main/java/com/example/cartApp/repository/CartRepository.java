package com.example.cartApp.repository;

import com.example.cartApp.model.Cart;
import com.example.cartApp.model.CartStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<Cart, UUID> {

    // koszyk user o danym statusie
    Optional<Cart> findByUserIdAndStatus(UUID userId, CartStatus status);

    // wszystkie koszyki danego usera
    List<Cart> findAllByUserId(UUID userId);
}