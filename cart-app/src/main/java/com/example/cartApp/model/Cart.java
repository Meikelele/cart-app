package com.example.cartApp.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "carts")
public class Cart {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "user_id", nullable = false, updatable = false)
    private UUID userId;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private CartStatus status;

    @Version
    @Column(name = "version")
    private Long version;

    // Relacja One-to-Many: JEDEN koszyk – WIELE pozycji
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<CartItem> items = new ArrayList<>();
    public Cart() {
    }

    public Cart(UUID userId) {
        this.id = UUID.randomUUID();
        this.userId = userId;
        this.status = CartStatus.OPEN;
    }


    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public CartStatus getStatus() {
        return status;
    }

    public void setStatus(CartStatus status) {
        this.status = status;
    }

    public Long getVersion() {
        return version;
    }

    public List<CartItem> getItems() {
        return items;
    }
    @Transient
    private BigDecimal totalValue;
    public void addItem(CartItem item) {
        items.add(item);
        item.setCart(this);
    }
    public BigDecimal getTotalValue() {
        if (items == null || items.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return items.stream()
                .map(item -> item.getPriceAtAddition().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }

    public void removeItem(CartItem item) {
        items.remove(item);
        item.setCart(null);
    }
}
