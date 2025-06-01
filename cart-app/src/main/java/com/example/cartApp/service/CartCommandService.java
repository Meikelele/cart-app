package com.example.cartApp.service;

import com.example.cartApp.model.Cart;
import java.util.UUID;

/**
 * Operacje modyfikujace stan koszyka (CQRS - komendy)
 */
public interface CartCommandService {
    Cart createCart(UUID userId);
    Cart addProductToCart(UUID cartId, UUID productId, int quantity);
    Cart removeProductFromCart(UUID cartId, UUID productId);
    Cart finalizeCart(UUID cartId);
}
