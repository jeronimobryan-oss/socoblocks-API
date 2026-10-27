package com.socoblocks.socoblocks.controller;

import com.socoblocks.socoblocks.dto.ProductResponse;
import com.socoblocks.socoblocks.service.CatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/catalogo")
@RequiredArgsConstructor
public class CatalogController {
    private final CatalogService catalogService;

    // Devuelve todos los sets activos
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllActives() {
        return ResponseEntity.ok(catalogService.listActive());
    }

    // Endpoint para buscar por nombre o descripción
    @GetMapping("/buscar")
    public ResponseEntity<List<ProductResponse>> search(@RequestParam(defaultValue = "") String q) {
        return ResponseEntity.ok(catalogService.search(q));
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(catalogService.getById(id));
    }


}
