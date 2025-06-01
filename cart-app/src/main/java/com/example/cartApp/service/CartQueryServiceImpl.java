package com.example.cartApp.service;

import com.example.cartApp.client.ProductClient;
import com.example.cartApp.model.Cart;
import com.example.cartApp.model.CartItem;
import com.example.cartApp.model.ProductDto;
import com.example.cartApp.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

/**
 * operacje odczytu danych koszyka
 */
@Service
public class CartQueryServiceImpl implements CartQueryService {

    private final CartRepository cartRepository;
    private final ProductClient productClient;

    @Autowired
    public CartQueryServiceImpl(CartRepository cartRepository, ProductClient productClient) {
        this.cartRepository = cartRepository;
        this.productClient = productClient;
    }

    @Override
    @Transactional(readOnly = true)
    /**
     * Pobiera koszyk o podanym identyfikatorze,
     * pobiera nowa cene i nazwe jesli sie zmienila w product-service
     */
    public Cart getCart(UUID cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("Koszyka nie znaleziono: " + cartId));

        for (CartItem item : cart.getItems()) {
            ProductDto pdto = productClient.getProductById(item.getProductId());
            item.setPriceAtAddition(pdto.getPrice());
            item.setProductName(pdto.getName());
        }
        return cart;
    }

    @Override
    @Transactional(readOnly = true)
    /**
     * pobranie wszystkie koszyki dla zadanego usera
     */
    public List<Cart> getUserCarts(UUID userId) {
        return cartRepository.findAllByUserId(userId);
    }
}
