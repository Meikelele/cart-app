package com.example.cartApp.service;

import com.example.cartApp.model.Cart;
import com.example.cartApp.model.CartStatus;
import com.example.cartApp.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import com.example.cartApp.client.ProductClient;
import com.example.cartApp.model.CartItem;
import com.example.cartApp.model.CartStatus;
import com.example.cartApp.model.ProductDto;
import com.example.cartApp.repository.CartItemRepository;

import java.math.BigDecimal;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductClient productClient;

    @Autowired
    public CartService(CartRepository cartRepository, CartItemRepository cartItemRepository, ProductClient productClient) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productClient = productClient;
    }

    /**
     * Tutaj sb tworze nowy koszyk dla danego user'a
     */
    @Transactional
    public Cart createCart(UUID userId) {
        // FIXME: jak jest koszyk OPEN dla usera to go mozna zwracac zaiast tworyzc nowy ale to kiedy indziej..
        Cart cart = new Cart(userId);
        return cartRepository.save(cart);
    }

    /**
     * Dodaje produkt do koszyka.
     */
    @Transactional
    public void addProductToCart(UUID cartId, UUID productId, int quantity) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new IllegalArgumentException("Koszyk nie istnieje: " + cartId));

        if (cart.getStatus() != CartStatus.OPEN) {
            throw new IllegalStateException("Koszyk nie jest OPEN - nie mozna dodac produktu");
        }

        ProductDto productDto = productClient.getProductById(productId);
        BigDecimal currentPrice = productDto.getPrice();

        CartItem item = new CartItem(productId, quantity, currentPrice);
        cart.addItem(item);

        cartRepository.save(cart);
    }

    /**
     * metoda do usuniecia produktu z koszyka
     */
    @Transactional
    public void removeProductFromCart(UUID cartId, UUID productId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new IllegalArgumentException("Koszyk nie istnieje: " + cartId));

        if (cart.getStatus() != CartStatus.OPEN) {
            throw new IllegalStateException("Koszyk nie jest OPEN - nie mozna usunac produktu");
        }

        CartItem toRemove = cart.getItems().stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Produkt nie jest w koszyku: " + productId));

        cart.removeItem(toRemove);
        cartRepository.save(cart);
    }


    /**
     * Wyciagam dany koszyk o jakims tam Id
     */
    @Transactional(readOnly = true)
    public Optional<Cart> getCartById(UUID cartId) {
        return cartRepository.findById(cartId);
    }
}