package com.example.cartApp.controller;

/**
 * DTO reprezentuje zadanie dodania produktu do koszyka
 */
public class AddProductRequest {
    private String productId;
    private int quantity;

    public AddProductRequest() {
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
