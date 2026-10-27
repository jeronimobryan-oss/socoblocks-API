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

            productRepository.save(
                    createProduct("El Cacahuatazo", "El choque accidental en pleno programa en vivo. Incluye podio de conductor y botarga en minifigura con cabeza intercambiable.",
                            new BigDecimal("550.00"), 25, 180, "https://res.cloudinary.com/drnyuznhx/image/upload/v1793132874/Screenshot_2026-10-27_132708_lr1bfb.png", true, Category.TV_NACIONAL)
            );

            productRepository.save(
                    createProduct("Se Vale: La Tirolesa", "El clásico momento del foro televisivo matutino con la tirolesa colgante que no frenó, cámaras de estudio y minifiguras de conductores despavoridos.",
                            new BigDecimal("1150.00"), 3, 420, "https://res.cloudinary.com/drnyuznhx/image/upload/v1793132926/copy_of_screenshot_2026-10-27_132700_qa7hys.png", true, Category.TV_NACIONAL)
            );

            // Agotado (stock 0)
            productRepository.save(
                    createProduct("El Ferras", "El célebre relato nocturno en Veracruz. Incluye minifigura con cadena dorada, celda desmontable y micro-periódico de noticias.",
                            new BigDecimal("650.00"), 0, 200, "https://res.cloudinary.com/drnyuznhx/image/upload/v1793132874/Screenshot_2026-10-27_132635_kptpw1.png", true, Category.ENTREVISTAS)
            );

            productRepository.save(
                    createProduct("La caída de Edgar", "Reconstruye el puente rústico sobre el río de San Carlos, el momento exacto donde Edgar suplica clemencia y el fatídico chapuzón con piezas de agua translúcida.",
                            new BigDecimal("899.00"), 15, 350, "https://res.cloudinary.com/drnyuznhx/image/upload/v1793132874/Screenshot_2026-10-27_132649_dgtdmx.png", true, Category.CLASICO_INTERNET)
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
