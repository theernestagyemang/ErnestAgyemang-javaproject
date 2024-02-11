package com.ernestagyemang.productorderservice.repository;

import com.ernestagyemang.productorderservice.model.Order;
import com.ernestagyemang.productorderservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUser(User user);
}
