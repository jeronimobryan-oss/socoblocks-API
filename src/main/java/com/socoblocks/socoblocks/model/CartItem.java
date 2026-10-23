package com.socoblocks.socoblocks.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cart_items",
        uniqueConstraints = @UniqueConstraint(columnNames = {"cart_id", "product_id"}))
@Getter
@NoArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne (optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id",nullable = false)
    private Cart cart;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private int quantity;

    //Contructor
    public CartItem(Cart cart, Product product, int quantity){
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
    }
    //Aumentar la cantidad de productos en el carrito
    public void increaseQuantity(int amount){
        this.quantity += amount;
    }
    // Reemplazar la cantidad de productos
    public void changeQuantity(int newQuantity) {
        this.quantity = newQuantity;
    }
}
