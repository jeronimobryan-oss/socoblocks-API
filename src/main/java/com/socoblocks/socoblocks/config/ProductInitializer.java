package com.socoblocks.socoblocks.config;

import com.socoblocks.socoblocks.model.Category;
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

            // Activo con stock
            productRepository.save(
                    createProduct("cetys", "Set de prueba del campus CETYS",
                            new BigDecimal("899.00"), 5, 1200, "https://example.com/cetys.png", true, Category.CLASICO_INTERNET)
            );

            // Activo sin stock (para probar disponible=false y OutOfStock en el carrito)
            productRepository.save(
                    createProduct("casa de aimep3", "Set de prueba de la casa de aimep3",
                            new BigDecimal("499.00"), 0, 650, "https://example.com/aimep3.png", true, Category.CLASICO_INTERNET)
            );

            // Inactivo (NO debe aparecer en GET /api/catalogo)
            productRepository.save(
                    createProduct("set inactivo", "Set desactivado, no debe verse en el catálogo",
                            new BigDecimal("299.00"), 10, 300, "https://example.com/inactivo.png", false, Category.CLASICO_INTERNET)
            );
        }
    }

    private Product createProduct(String name, String description, BigDecimal price, int stock,
                                  int pieceCount, String imageUrl, boolean active, Category category) {
        Product product = new Product(name, price, stock, description, imageUrl, pieceCount, active, category);
        product.setDescription(description);
        product.setPieceCount(pieceCount);
        product.setImageUrl(imageUrl);
        product.setActive(active);
        return product;
    }
}
