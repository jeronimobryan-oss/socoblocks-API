package com.socoblocks.socoblocks.controller;
import com.socoblocks.socoblocks.dto.*;
import com.socoblocks.socoblocks.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carrito")
@RequiredArgsConstructor
public class CartController {


    private final CartService cartService;

    // GET /api/carrito
    @GetMapping
    public ResponseEntity<CartResponse> getCart(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(cartService.getCart(jwt.getSubject()));
    }

    // POST /api/carrito/items
    @PostMapping("/items")
    public ResponseEntity<CartResponse> addItem(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody AddCartItemRequest request) {

        CartResponse response = cartService.addItem(jwt.getSubject(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // PUT /api/carrito/items/{id}
    @PutMapping("/items/{id}")
    public ResponseEntity<CartResponse> updateItem(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable Long id,
            @Valid @RequestBody UpdateCartItemRequest request) {

        return ResponseEntity.ok(cartService.updateItem(jwt.getSubject(), id, request));
    }

    // DELETE /api/carrito/items/{id}
    @DeleteMapping("/items/{id}")
    public ResponseEntity<CartResponse> removeItem(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable Long id) {

        return ResponseEntity.ok(cartService.removeItem(jwt.getSubject(), id));
    }
}
