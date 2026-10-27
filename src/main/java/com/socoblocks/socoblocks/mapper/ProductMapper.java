package com.socoblocks.socoblocks.mapper;

import com.socoblocks.socoblocks.dto.ProductResponse;
import com.socoblocks.socoblocks.model.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper implements BaseMapper<Product, ProductResponse>{
    @Override
    public Product mapDtoToEntity(ProductResponse dto) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ProductResponse mapEntityToDTO(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getPieceCount(),
                product.getImageUrl(),
                product.getCategory(),
                product.getStock(),
                product.isActive(),
                product.getStock() > 0
        );
    }
}
