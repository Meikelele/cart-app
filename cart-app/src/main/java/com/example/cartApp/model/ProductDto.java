package com.example.cartApp.model;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * obiekt na dane przychodzace z product service
 * posredniczy w wymainie meidzy Cart Service a Product Service
 */
public class ProductDto {

    private UUID id;
    private String name;
    private BigDecimal price;

    public ProductDto() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}