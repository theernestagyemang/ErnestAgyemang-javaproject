package com.ernestagyemang.productorderservice.repository;

import com.ernestagyemang.productorderservice.model.Order;
import com.ernestagyemang.productorderservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUser(User user);
}
