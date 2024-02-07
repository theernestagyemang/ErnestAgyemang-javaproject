package com.ernestagyemang.productorderservice.repository;

import com.ernestagyemang.productorderservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
