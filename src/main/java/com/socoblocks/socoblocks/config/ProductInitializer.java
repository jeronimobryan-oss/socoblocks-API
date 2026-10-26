package com.socoblocks.socoblocks.config;

import com.socoblocks.socoblocks.model.Product;
import com.socoblocks.socoblocks.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class ProductInitializer implements ApplicationRunner {

    private final ProductRepository productRepository;

    @Override
    public void run(ApplicationArguments args) {

        if (productRepository.count() == 0) {

            productRepository.save(
                    new Product("cetys", new BigDecimal("899.00"), 5) //prubea
            );

            productRepository.save(
                    new Product("casa de aimep3", new BigDecimal("499.00"), 0) //Prueba
            );
        }
    }
}
