
package com.socoblocks.socoblocks.mapper;

import com.socoblocks.socoblocks.dto.CartItemResponse;
import com.socoblocks.socoblocks.dto.CartResponse;
import com.socoblocks.socoblocks.model.Cart;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CartMapper implements BaseMapper<Cart, CartResponse> {

    @Override
    public Cart mapDtoToEntity(CartResponse dto) {
        throw new UnsupportedOperationException(
                "CartMapper does not support DTO to entity conversion"
        );
    }

    @Override
    public CartResponse mapEntityToDTO(Cart cart) {

        // 1. Convertir las líneas del carrito en respuestas
        List<CartItemResponse> items = cart.getItems()
                .stream()
                .map(item -> {
                    BigDecimal subtotal = item.getProduct()
                            .getPrice()
                            .multiply(BigDecimal.valueOf(item.getQuantity()));

                    return new CartItemResponse(
                            item.getId(),
                            item.getProduct().getId(),
                            item.getProduct().getName(),
                            item.getProduct().getPrice(),
                            item.getQuantity(),
                            subtotal
                    );
                })
                .toList();

        // 2. Calcular el número total de unidades
        int totalItems = items.stream()
                .mapToInt(CartItemResponse::quantity)
                .sum();

        // 3. Sumar los subtotales de todas las líneas
        BigDecimal total = items.stream()
                .map(CartItemResponse::subtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 4. Construir la respuesta del carrito
        return new CartResponse(
                cart.getId(),
                items,
                totalItems,
                total
        );
    }
}
