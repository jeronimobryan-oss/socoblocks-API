package com.socoblocks.socoblocks.controller;

import com.socoblocks.socoblocks.dto.ProductSummaryResponse;
import com.socoblocks.socoblocks.service.CatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/catalogo")
@RequiredArgsConstructor
public class CatalogController {
    private final CatalogService catalogService;

    // Devuelve todos los sets activos
    @GetMapping
    public ResponseEntity<List<ProductSummaryResponse>> getAllActives() {
        return ResponseEntity.ok(catalogService.listActive());
    }
}
