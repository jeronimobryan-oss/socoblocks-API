package com.socoblocks.socoblocks.service;

import com.socoblocks.socoblocks.dto.AddCartItemRequest;
import com.socoblocks.socoblocks.dto.CartResponse;
import com.socoblocks.socoblocks.dto.UpdateCartItemRequest;
import com.socoblocks.socoblocks.exception.CartItemNotFoundException;
import com.socoblocks.socoblocks.exception.OutOfStockException;
import com.socoblocks.socoblocks.exception.ProductNotFoundException;
import com.socoblocks.socoblocks.mapper.BaseMapper;
import com.socoblocks.socoblocks.model.Cart;
import com.socoblocks.socoblocks.model.CartItem;
import com.socoblocks.socoblocks.model.Product;
import com.socoblocks.socoblocks.repository.AppUserRepository;
import com.socoblocks.socoblocks.repository.CartRepository;
import com.socoblocks.socoblocks.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final AppUserRepository appUserRepository;
    private final BaseMapper<Cart, CartResponse> cartMapper;


    @Transactional
    public CartResponse addItem(String username, AddCartItemRequest request) {

        //Buscar el producto
        Product product = productRepository.findById(request.productId())
                .orElseThrow(() ->
                        new ProductNotFoundException(request.productId()));

        //Validar que haya stock
        if (product.getStock() <= 0) {
            throw new OutOfStockException(product.getName());
        }

        //Obtener o crear el carrito
        Cart cart = cartRepository.findByUserUsername(username)
                .orElseGet(() -> {
                    var user = appUserRepository.findByUsername(username)
                            .orElseThrow(() ->
                                    new UsernameNotFoundException("User not found"));

                    return cartRepository.save(new Cart(user));
                });

        //Validar el stock considerando lo que ya está en el carrito
        int currentQuantity = cart.findItem(product.getId())
                .map(CartItem::getQuantity)
                .orElse(0);

        if (currentQuantity + request.quantity() > product.getStock()) {
            throw new OutOfStockException(product.getName());
        }

        //gregar el producto, guardar y responder
        cart.addItem(product, request.quantity());

        Cart savedCart = cartRepository.save(cart);

        return cartMapper.mapEntityToDTO(savedCart);
    }
    // GET /api/carrito
    @Transactional(readOnly = true)
    public CartResponse getCart(String username) {
        // Si el usuario aún no tiene carrito, se devuelve uno vacío (no es un error)
        return cartRepository.findByUserUsername(username)
                .map(cartMapper::mapEntityToDTO)
                .orElseGet(() -> new CartResponse(null, List.of(), 0, BigDecimal.ZERO));
    }

    // PUT /api/carrito/items/{id}
    @Transactional
    public CartResponse updateItem(String username, Long itemId, UpdateCartItemRequest request) {
        Cart cart = findCartOrThrow(username, itemId);
        CartItem item = findItemOrThrow(cart, itemId);

        // Validar la nueva cantidad contra el stock
        Product product = item.getProduct();
        if (request.quantity() > product.getStock()) {
            throw new OutOfStockException(product.getName());
        }

        // Aquí la cantidad se REEMPLAZA (en el POST se suma)
        item.changeQuantity(request.quantity());

        // No hace falta save(): al ser @Transactional, Hibernate guarda el cambio solo
        return cartMapper.mapEntityToDTO(cart);
    }

    // DELETE /api/carrito/items/{id}
    @Transactional
    public CartResponse removeItem(String username, Long itemId) {
        Cart cart = findCartOrThrow(username, itemId);
        CartItem item = findItemOrThrow(cart, itemId);

        // orphanRemoval = true borra la línea de la base de datos
        cart.removeItem(item);

        return cartMapper.mapEntityToDTO(cart);
    }



    // Carrito del usuario; si no tiene, la línea pedida tampoco existe (404)
    private Cart findCartOrThrow(String username, Long itemId) {
        return cartRepository.findByUserUsername(username)
                .orElseThrow(() -> new CartItemNotFoundException(itemId));
    }

    // Busca la línea SOLO dentro del carrito del usuario: nadie puede tocar el carrito de otro
    private CartItem findItemOrThrow(Cart cart, Long itemId) {
        return cart.findItemById(itemId)
                .orElseThrow(() -> new CartItemNotFoundException(itemId));
    }
}
