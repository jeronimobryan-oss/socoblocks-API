package com.socoblocks.socoblocks.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
public class Product {
    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false, length = 2000)
    private String description;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private int pieceCount;

    @Column(nullable = false)
    private boolean active;

    @Column(nullable = false)
    private int stock;

    @Column(nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private AppUser seller;

    //Constructor para agregar productos
    public Product(String name, BigDecimal price, int stock, String description, String imageUrl, int pieceCount, boolean active, Category category) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.description = description;
        this.imageUrl = imageUrl;
        this.pieceCount = pieceCount;
        this.active = active;
        this.category = category;
    }
}