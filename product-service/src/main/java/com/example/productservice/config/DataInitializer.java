package com.example.productservice.config;

import com.example.productservice.model.Product;
import com.example.productservice.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.UUID;

@Configuration
public class DataInitializer {

    @Bean
    /**
     * Uruchamia si eprzy starcie apliakcji,
     * Wstawia przykladowe produkty jesli tabela jest pusta
     */
    public CommandLineRunner loadData(ProductRepository productRepository) {
        return args -> {
            if (productRepository.count() == 0) {
                Product p1 = new Product(
                        UUID.fromString("11111111-1111-1111-1111-111111111111"),
                        "Czekolada Milka 100g",
                        BigDecimal.valueOf(4.99)
                );
                Product p2 = new Product(
                        UUID.fromString("22222222-2222-2222-2222-222222222222"),
                        "Chleb Szelc 500g",
                        BigDecimal.valueOf(2.49)
                );
                Product p3 = new Product(
                        UUID.fromString("33333333-3333-3333-3333-333333333333"),
                        "Mleko UHT 0.5% 1l",
                        BigDecimal.valueOf(3.19)
                );
                Product p4 = new Product(
                        UUID.fromString("44444444-4444-4444-4444-444444444444"),
                        "Rurki Waniliowe 280g",
                        BigDecimal.valueOf(3.19)
                );
                Product p5 = new Product(
                        UUID.fromString("55555555-5555-5555-5555-555555555555"),
                        "Zupka Chińska 50g",
                        BigDecimal.valueOf(3.19)
                );
                Product p6 = new Product(
                        UUID.fromString("66666666-6666-6666-6666-666666666666"),
                        "Celestynka Pomaranczowa 1,85L",
                        BigDecimal.valueOf(3.19)
                );

                productRepository.save(p1);
                productRepository.save(p2);
                productRepository.save(p3);
                productRepository.save(p4);
                productRepository.save(p5);
                productRepository.save(p6);

                System.out.println(">>> przykładowe produkty:");
                System.out.println("    - " + p1.getName() + " (ID=" + p1.getId() + ")");
                System.out.println("    - " + p2.getName() + " (ID=" + p2.getId() + ")");
                System.out.println("    - " + p3.getName() + " (ID=" + p3.getId() + ")");
                System.out.println("    - " + p4.getName() + " (ID=" + p4.getId() + ")");
                System.out.println("    - " + p5.getName() + " (ID=" + p5.getId() + ")");
                System.out.println("    - " + p6.getName() + " (ID=" + p6.getId() + ")");
            } else {
                System.out.println(">>> tabela products nie jest pusta, nie wstaiwam danych");
            }
        };
    }
}
