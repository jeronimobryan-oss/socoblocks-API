package com.socoblocks.socoblocks.dto;

import com.socoblocks.socoblocks.model.Category;

import java.math.BigDecimal;

public record ProductResponse(Long id,
                              String name,
                              String description,
                              BigDecimal price,
                              int pieceCount,
                              String imageUrl,
                              Category category,
                              int stock,
                              boolean active,
                              boolean available) {
}
