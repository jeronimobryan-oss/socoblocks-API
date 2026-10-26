package com.socoblocks.socoblocks.dto;

import com.socoblocks.socoblocks.model.Category;
import com.socoblocks.socoblocks.repository.ProductRepository;

import java.math.BigDecimal;

public record ProductSummaryResponse(Long id,
                                     String name,
                                     BigDecimal price,
                                     int pieceCount,
                                     String imageUrl,
                                     Category category,
                                     boolean available) {
}
