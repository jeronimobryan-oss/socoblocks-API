package com.socoblocks.socoblocks.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "carts")
@Getter
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(
            name="user_id",
            nullable = false,
            unique = true
    )
    private AppUser user;

    @OneToMany(
            mappedBy = "cart",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<CartItem> items = new ArrayList<>();

    //Construccion
    public Cart(AppUser user) {
        this.user = user;
    }
    //METODO 1 PARA BUSCAR UNA LINEA POR EL ID DEL PROCUTO
    public Optional<CartItem> findItem(Long productId) {
        return items.stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();
    }

    //METODO 2 PARA AGREGAR UN PORDUCTO O AUMENTAR SU CANTIDAD DEL MISMO
    public CartItem addItem(Product product, int quantity) {
        Optional<CartItem> existing = findItem(product.getId());

        if (existing.isPresent()) {
            CartItem item = existing.get();
            item.increaseQuantity(quantity);
            return item;
        }

        CartItem newItem = new CartItem(this, product, quantity);
        items.add(newItem);
        return newItem;
    }
    // Eliminar un producto del carrito
    public void removeItem(CartItem item) {
        items.remove(item);
    }
    public Optional<CartItem> findItemById(Long itemId) {
        return items.stream()
                .filter(item -> itemId.equals(item.getId()))
                .findFirst();
    }
}
