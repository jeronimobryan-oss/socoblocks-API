package com.socoblocks.socoblocks.service;

import com.socoblocks.socoblocks.dto.ProductResponse;
import com.socoblocks.socoblocks.exception.ProductNotFoundException;
import com.socoblocks.socoblocks.mapper.BaseMapper;
import com.socoblocks.socoblocks.model.Product;
import com.socoblocks.socoblocks.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CatalogService {
    private final ProductRepository productRepository;
    private final BaseMapper<Product, ProductResponse> mapper;

    // Regresa la lista de productos activos
    public List<ProductResponse> listActive() {
        return productRepository.findByActiveTrue()
                .stream()
                .map(mapper::mapEntityToDTO)
                .toList();
    }

    // Buscar un producto por nombre o descripción
    public List<ProductResponse> search(String q) {
        String text = q == null ? "" : q.trim();

        if (text.isEmpty()) {
            return List.of();
        }

        return productRepository.searchActive(text)
                .stream()
                .map(mapper::mapEntityToDTO)
                .toList();
    }

    // Buscar producto por ID
    public ProductResponse getById(Long id) {
        Product product = productRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        return mapper.mapEntityToDTO(product);
    }





}
