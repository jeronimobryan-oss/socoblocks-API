package com.socoblocks.socoblocks.repository;

import com.socoblocks.socoblocks.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Regresar todos los productos activos
    List<Product> findByActiveTrue();

    @Query("""
        SELECT p FROM Product p
        WHERE p.active = true
          AND (LOWER(p.name) LIKE LOWER(CONCAT('%', :q, '%'))
            OR LOWER(p.description) LIKE LOWER(CONCAT('%', :q, '%')))
        """)
    List<Product> searchActive(@Param("q") String q);

    // Buscar producto Activo por ID
    Optional<Product> findByIdAndActiveTrue(Long id);



}
