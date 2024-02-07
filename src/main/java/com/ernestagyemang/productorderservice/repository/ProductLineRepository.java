package com.ernestagyemang.productorderservice.repository;

import com.ernestagyemang.productorderservice.model.Order;
import com.ernestagyemang.productorderservice.model.ProductLine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductLineRepository extends JpaRepository<ProductLine, Long> {
    List<ProductLine> findAllByOrder(Order order);
}
