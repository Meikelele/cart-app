package com.example.cartApp.service;

import com.example.cartApp.model.Cart;
import java.util.List;
import java.util.UUID;

/**
 * operacje do odczytu danych z koszyka (CQRS - zapytanie)
 * metoda do wyswietlenia koszyka
 */
public interface CartQueryService {
    Cart getCart(UUID cartId);
    List<Cart> getUserCarts(UUID userId);
}
