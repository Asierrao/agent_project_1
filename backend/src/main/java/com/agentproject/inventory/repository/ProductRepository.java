package com.agentproject.inventory.repository;

import com.agentproject.inventory.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByProductNo(String productNo);
    Optional<Product> findByProductNo(String productNo);
}
