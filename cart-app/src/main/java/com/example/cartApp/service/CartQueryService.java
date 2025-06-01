package com.example.cartApp.service;

import com.example.cartApp.model.Cart;
import java.util.List;
import java.util.UUID;

public interface CartQueryService {
    Cart getCart(UUID cartId);
    List<Cart> getUserCarts(UUID userId);
}
