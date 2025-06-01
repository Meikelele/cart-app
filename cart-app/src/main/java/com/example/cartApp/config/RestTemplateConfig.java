package com.example.cartApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * wbudowany klient HTTP ktory ulatwia wysylanie zadan HTTP,
 * automatycznie konwertuje odpowiedzi JSON na obiekty Javy
 */
@Configuration
public class RestTemplateConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}