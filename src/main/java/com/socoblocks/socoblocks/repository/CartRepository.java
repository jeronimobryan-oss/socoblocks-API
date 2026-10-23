package com.socoblocks.socoblocks.repository;

import com.socoblocks.socoblocks.model.Cart;
import com.socoblocks.socoblocks.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUserUsername(String username);

}
