package com.example.cartApp.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "cart_items")
public class CartItem {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "product_id", nullable = false, updatable = false)
    private UUID productId;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "price_at_addition", nullable = false)
    private BigDecimal priceAtAddition;

    // Relacja Many-to-One: wiele pozycji należy do jednego koszyka
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    public CartItem() {
    }

    // Wygodny konstruktor
    public CartItem(UUID productId, int quantity, BigDecimal priceAtAddition) {
        this.id = UUID.randomUUID();
        this.productId = productId;
        this.quantity = quantity;
        this.priceAtAddition = priceAtAddition;
    }

    // Gettery i Settery

    public UUID getId() {
        return id;
    }

    public UUID getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPriceAtAddition() {
        return priceAtAddition;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}
