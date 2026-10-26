package com.socoblocks.socoblocks.service;

import com.socoblocks.socoblocks.dto.ProductSummaryResponse;
import com.socoblocks.socoblocks.mapper.BaseMapper;
import com.socoblocks.socoblocks.model.Product;
import com.socoblocks.socoblocks.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.AjAttribute;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CatalogService {
    private final ProductRepository productRepository;
    private final BaseMapper<Product, ProductSummaryResponse> mapper;

    // Regresa la lista de productos activos
    @Transactional(readOnly = true)
    public List<ProductSummaryResponse> listActive() {
        return productRepository.findByActiveTrue()
                .stream()
                .map(mapper::mapEntityToDTO)
                .toList();
    }



}
