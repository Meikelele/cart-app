package com.example.cartApp.controller;

import com.example.cartApp.model.Cart;
import com.example.cartApp.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/carts")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    /**
     * Endpoint do tworzenia nowego koszyka.
     */
    @PostMapping
    public ResponseEntity<Cart> createCart(@RequestBody CreateCartRequest request) {
        try {
            UUID userId = UUID.fromString(request.getUserId());
            Cart created = cartService.createCart(userId);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            // Błąd, gdy userId nie jest poprawnym UUID
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Endpoint do pobrania koszyka po ID.
     */
    @GetMapping("/{cartId}")
    public ResponseEntity<Cart> getCart(@PathVariable String cartId) {
        try {
            UUID id = UUID.fromString(cartId);
            Optional<Cart> cartOpt = cartService.getCartById(id);
            if (cartOpt.isPresent()) {
                return ResponseEntity.ok(cartOpt.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}