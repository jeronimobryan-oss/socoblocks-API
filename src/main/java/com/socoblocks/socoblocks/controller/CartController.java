package com.socoblocks.socoblocks.controller;
import com.socoblocks.socoblocks.dto.*;
import com.socoblocks.socoblocks.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/carrito")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/items")
    public ResponseEntity<CartResponse> addItem(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody AddCartItemRequest request) {

        CartResponse response = cartService.addItem(
                jwt.getSubject(),
                request
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
