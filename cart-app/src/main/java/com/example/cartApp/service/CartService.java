package com.example.cartApp.service;

import com.example.cartApp.model.Cart;
import com.example.cartApp.model.CartStatus;
import com.example.cartApp.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class CartService {

    private final CartRepository cartRepository;

    @Autowired
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
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
     * Wyciagam dany koszyk o jakims tam Id
     */
    @Transactional(readOnly = true)
    public Optional<Cart> getCartById(UUID cartId) {
        return cartRepository.findById(cartId);
    }
}