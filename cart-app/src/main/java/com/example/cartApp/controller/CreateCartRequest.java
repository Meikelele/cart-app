package com.example.cartApp.controller;

/**
 * DTO reprezentuje zadanie utworzenia nowego koszyka
 */
public class CreateCartRequest {
    private String userId;

    public CreateCartRequest() {
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
