package com.ernestagyemang.productorderservice.service.interfaces;

import com.ernestagyemang.productorderservice.dto.OrderInput;
import com.ernestagyemang.productorderservice.model.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    List<Order> getAllOrders();

    List<Order> getAllOrdersByUser(String email);

    Order getOrderById(Long id);

    Order createOrder(OrderInput orderInput);

    Order updateOrder(OrderInput orderInput);

    String deleteOrder(Long id);
}
