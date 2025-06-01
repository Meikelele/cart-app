package com.example.cartApp.client;

import com.example.cartApp.model.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

/**
 * komunikacja z product-service
 * pobieranie danych o produkcie na podstawie UUID
 */
@Component
public class ProductClient {

    private final RestTemplate restTemplate;
    private final String productServiceUrl;

    @Autowired
    public ProductClient(RestTemplate restTemplate, @Value("${product.service.url}") String productServiceUrl) {
        this.restTemplate = restTemplate;
        this.productServiceUrl = productServiceUrl;
    }

    /**
     * zadanie GET do product-service,
     * wyciagam dane po UUID
     * deserializacja do dto
     */
    public ProductDto getProductById(UUID productId) {
        String url = productServiceUrl + "/" + productId;
        try {
            ResponseEntity<ProductDto> response = restTemplate.getForEntity(url, ProductDto.class);
            return response.getBody();
        } catch (RestClientException ex) {
            throw new RuntimeException("Problem z fetch produktow by id: " + ex.getMessage());
        }
    }
}
