package com.socoblocks.socoblocks.service;

import com.socoblocks.socoblocks.dto.AddCartItemRequest;
import com.socoblocks.socoblocks.dto.CartResponse;
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

}
