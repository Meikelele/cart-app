package com.example.cartApp.controller;

import com.example.cartApp.model.Cart;
import com.example.cartApp.service.CartCommandService;
import com.example.cartApp.service.CartQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/carts")
public class CartController {

    private final CartCommandService commandService;
    private final CartQueryService queryService;

    @Autowired
    public CartController(CartCommandService commandService,
                          CartQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    /** Tworzenie nowego koszyka */
    @PostMapping
    public ResponseEntity<Cart> createCart(@RequestBody CreateCartRequest request) {
        try {
            UUID userId = UUID.fromString(request.getUserId());
            Cart created = commandService.createCart(userId);
            return ResponseEntity.status(201).body(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /** Pobranie koszyka po ID */
    @GetMapping("/{cartId}")
    public ResponseEntity<Cart> getCart(@PathVariable String cartId) {
        try {
            UUID id = UUID.fromString(cartId);
            Cart cart = queryService.getCart(id);
            return ResponseEntity.ok(cart);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /** Dodanie produktu do koszyka */
    @PostMapping("/{cartId}/items")
    public ResponseEntity<String> addProduct(
            @PathVariable String cartId,
            @RequestBody AddProductRequest request) {
        try {
            UUID cId = UUID.fromString(cartId);
            UUID pId = UUID.fromString(request.getProductId());
            Cart updated = commandService.addProductToCart(cId, pId, request.getQuantity());
            return ResponseEntity.status(201).body("Added, cart now has " + updated.getItems().size() + " items.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Bad request: " + e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(409).body("Conflict: " + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body("Server error: " + e.getMessage());
        }
    }

    /** Usunięcie produktu z koszyka */
    @DeleteMapping("/{cartId}/items/{productId}")
    public ResponseEntity<String> removeProduct(
            @PathVariable String cartId,
            @PathVariable String productId) {
        try {
            UUID cId = UUID.fromString(cartId);
            UUID pId = UUID.fromString(productId);
            Cart updated = commandService.removeProductFromCart(cId, pId);
            return ResponseEntity.ok("Removed, cart now has " + updated.getItems().size() + " items.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Bad request: " + e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(409).body("Conflict: " + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body("Server error: " + e.getMessage());
        }
    }

    /** Finalizacja koszyka */
    @PostMapping("/{cartId}/finalize")
    public ResponseEntity<Void> finalizeCart(@PathVariable String cartId) {
        try {
            UUID cId = UUID.fromString(cartId);
            commandService.finalizeCart(cId);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(409).build();
        }
    }
}
