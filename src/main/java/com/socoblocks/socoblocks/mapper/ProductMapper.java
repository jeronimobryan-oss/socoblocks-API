package com.socoblocks.socoblocks.mapper;

import com.socoblocks.socoblocks.dto.ProductSummaryResponse;
import com.socoblocks.socoblocks.model.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper implements BaseMapper<Product, ProductSummaryResponse>{
    @Override
    public Product mapDtoToEntity(ProductSummaryResponse dto) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ProductSummaryResponse mapEntityToDTO(Product product) {
        return new ProductSummaryResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getPieceCount(),
                product.getImageUrl(),
                product.getCategory(),
                product.getStock() > 0

        );

    }
}
