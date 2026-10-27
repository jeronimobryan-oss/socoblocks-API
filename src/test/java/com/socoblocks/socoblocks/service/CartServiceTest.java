package com.socoblocks.socoblocks.service;

import com.socoblocks.socoblocks.dto.AddCartItemRequest;
import com.socoblocks.socoblocks.dto.CartResponse;
import com.socoblocks.socoblocks.dto.UpdateCartItemRequest;
import com.socoblocks.socoblocks.exception.CartItemNotFoundException;
import com.socoblocks.socoblocks.exception.OutOfStockException;
import com.socoblocks.socoblocks.exception.ProductNotFoundException;
import com.socoblocks.socoblocks.mapper.CartMapper;
import com.socoblocks.socoblocks.model.AppUser;
import com.socoblocks.socoblocks.model.Cart;
import com.socoblocks.socoblocks.model.CartItem;
import com.socoblocks.socoblocks.model.Product;
import com.socoblocks.socoblocks.model.Role;
import com.socoblocks.socoblocks.repository.AppUserRepository;
import com.socoblocks.socoblocks.repository.CartRepository;
import com.socoblocks.socoblocks.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    private static final String JARED = "jared@correo.com";

    @Mock CartRepository cartRepository;
    @Mock ProductRepository productRepository;
    @Mock AppUserRepository appUserRepository;

    CartService service;
    AppUser user;
    Product edgar;

    @BeforeEach
    void setUp() {
        // Mismo orden de campos que CartService
        service = new CartService(cartRepository, productRepository, appUserRepository, new CartMapper());
        user = new AppUser(JARED, "hash", Role.COLECCIONISTA);
        edgar = new Product("La caída de Edgar", new BigDecimal("899.00"), 5,
                "Set de prueba", "/sets/edgar.png", 500, true, null);
        edgar.setId(1L);
    }

    // Carrito con una línea de 'cantidad' unidades y id de línea 10
    private Cart carritoCon(int cantidad) {
        Cart cart = new Cart(user);
        CartItem item = cart.addItem(edgar, cantidad);
        ReflectionTestUtils.setField(item, "id", 10L);
        return cart;
    }

    @Test
    void agregarProductoInexistenteLanzaNotFound() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.addItem(JARED, new AddCartItemRequest(99L, 1)))
                .isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    void agregarProductoSinStockLanzaOutOfStock() {
        edgar.setStock(0);
        when(productRepository.findById(1L)).thenReturn(Optional.of(edgar));
        assertThatThrownBy(() -> service.addItem(JARED, new AddCartItemRequest(1L, 1)))
                .isInstanceOf(OutOfStockException.class);
        verify(cartRepository, never()).save(any());
    }

    @Test
    void agregarCreaCarritoSiNoExisteYCalculaTotal() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(edgar));
        when(cartRepository.findByUserUsername(JARED)).thenReturn(Optional.empty());
        when(appUserRepository.findByUsername(JARED)).thenReturn(Optional.of(user));
        when(cartRepository.save(any(Cart.class))).thenAnswer(inv -> inv.getArgument(0));

        CartResponse res = service.addItem(JARED, new AddCartItemRequest(1L, 2));

        assertThat(res.totalItems()).isEqualTo(2);
        assertThat(res.total()).isEqualByComparingTo("1798.00");
    }

    @Test
    void noPermiteSuperarStockSumandoLoQueYaHayEnElCarrito() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(edgar));
        when(cartRepository.findByUserUsername(JARED)).thenReturn(Optional.of(carritoCon(4)));
        assertThatThrownBy(() -> service.addItem(JARED, new AddCartItemRequest(1L, 2)))
                .isInstanceOf(OutOfStockException.class);
    }

    @Test
    void actualizarCantidadMayorAlStockLanzaOutOfStock() {
        when(cartRepository.findByUserUsername(JARED)).thenReturn(Optional.of(carritoCon(1)));
        assertThatThrownBy(() -> service.updateItem(JARED, 10L, new UpdateCartItemRequest(6)))
                .isInstanceOf(OutOfStockException.class);
    }

    @Test
    void actualizarCantidadValidaReemplazaYRecalcula() {
        when(cartRepository.findByUserUsername(JARED)).thenReturn(Optional.of(carritoCon(1)));
        CartResponse res = service.updateItem(JARED, 10L, new UpdateCartItemRequest(3));
        assertThat(res.totalItems()).isEqualTo(3);
        assertThat(res.total()).isEqualByComparingTo("2697.00");
    }

    @Test
    void eliminarLineaQueNoEstaEnMiCarritoLanzaNotFound() {
        when(cartRepository.findByUserUsername(JARED)).thenReturn(Optional.of(new Cart(user)));
        assertThatThrownBy(() -> service.removeItem(JARED, 10L))
                .isInstanceOf(CartItemNotFoundException.class);
    }

    @Test
    void verCarritoSinCarritoDevuelveVacio() {
        when(cartRepository.findByUserUsername(JARED)).thenReturn(Optional.empty());
        CartResponse res = service.getCart(JARED);
        assertThat(res.items()).isEmpty();
        assertThat(res.total()).isEqualByComparingTo("0");
    }
}