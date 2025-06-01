package com.example.cartApp.service;

import com.example.cartApp.client.ProductClient;
import com.example.cartApp.model.Cart;
import com.example.cartApp.model.CartItem;
import com.example.cartApp.model.ProductDto;
import com.example.cartApp.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class CartQueryServiceImpl implements CartQueryService {

    private final CartRepository cartRepository;
    private final ProductClient productClient;

    @Autowired
    public CartQueryServiceImpl(CartRepository cartRepository, ProductClient productClient) {
        this.cartRepository = cartRepository;
        this.productClient = productClient;
    }

    @Override
    @Transactional(readOnly = true)
    public Cart getCart(UUID cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("Koszyka nie znaleziono: " + cartId));

        // Aktualizacja pozycji (pobranie nazwy i ceny dla każdej pozycji)
        for (CartItem item : cart.getItems()) {
            ProductDto pdto = productClient.getProductById(item.getProductId());
            item.setPriceAtAddition(pdto.getPrice());
            item.setProductName(pdto.getName());
        }
        return cart;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cart> getUserCarts(UUID userId) {
        return cartRepository.findAllByUserId(userId);
    }
}
