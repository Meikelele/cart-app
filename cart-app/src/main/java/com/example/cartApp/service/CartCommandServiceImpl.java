package com.example.cartApp.service;

import com.example.cartApp.client.ProductClient;
import com.example.cartApp.model.Cart;
import com.example.cartApp.model.CartItem;
import com.example.cartApp.model.CartStatus;
import com.example.cartApp.model.ProductDto;
import com.example.cartApp.repository.CartItemRepository;
import com.example.cartApp.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

/**
 * operacje modyfikujące stan koszyka
 */
@Service
public class CartCommandServiceImpl implements CartCommandService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductClient productClient;

    @Autowired
    public CartCommandServiceImpl(CartRepository cartRepository, CartItemRepository cartItemRepository, ProductClient productClient) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productClient = productClient;
    }

    /**
     * Tworzy nowy koszyk dla podanego użytkownika,
     * OPEN dla koszyka
     */
    @Override
    @Transactional
    public Cart createCart(UUID userId) {
        Cart cart = new Cart(userId);
        cart.setStatus(CartStatus.OPEN);
        return cartRepository.save(cart);
    }

    @Override
    @Transactional
    /**
     * Dodaje określoną ilość produktu do istniejącego koszyka
     */
    public Cart addProductToCart(UUID cartId, UUID productId, int quantity) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new IllegalArgumentException("Koszyk nie znaleziony: " + cartId));
        if (cart.getStatus() != CartStatus.OPEN) {
            throw new IllegalStateException("Nie mozna dodac produktu do nie OPEN koszyka");
        }

        // szczegóły produktu z Product Service
        ProductDto pdto = productClient.getProductById(productId);

        // Jeśli pozycja już istnieje w koszyku, zwiększa jej ilość
        CartItem existing = cart.getItems().stream()
                .filter(i -> i.getProductId().equals(productId))
                .findFirst()
                .orElse(null);

        if (existing != null) {
            existing.setQuantity(existing.getQuantity() + quantity);
            cartItemRepository.save(existing);
        } else {
            CartItem newItem = new CartItem(productId, quantity, pdto.getPrice());
            newItem.setCart(cart);
            cart.getItems().add(newItem);
            cartItemRepository.save(newItem);
        }

        return cart;
    }

    @Override
    @Transactional
    /**
     * Usuwa produkt z koszyka, jeśli koszyk ma status OPEN
     */
    public Cart removeProductFromCart(UUID cartId, UUID productId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("Koszyka nie znaleziono o id: " + cartId));
        if (cart.getStatus() != CartStatus.OPEN) {
            throw new IllegalStateException("Koszyk nie jest OPEN nie mozna usunac produktu");
        }

        CartItem existing = cart.getItems().stream()
                .filter(i -> i.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Produktu nie ma w koszyku: " + productId));

        cart.getItems().remove(existing);
        cartItemRepository.delete(existing);
        return cart;
    }

    @Override
    @Transactional
    /**
     * Finalizacja koszyka
     */
    public Cart finalizeCart(UUID cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new IllegalArgumentException("Koszyka nie znaleziono o id: " + cartId));
        if (cart.getStatus() != CartStatus.OPEN) {
            throw new IllegalStateException("Koszyk jest FINALIZED");
        }
        cart.setStatus(CartStatus.FINALIZED);
        return cartRepository.save(cart);
    }
}
