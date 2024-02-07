package com.ernestagyemang.productorderservice.service.interfaces;

import com.ernestagyemang.productorderservice.dto.OrderDto;
import com.ernestagyemang.productorderservice.model.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    List<Order> getAllOrders();

    List<Order> getAllOrdersByUser(Long userId);

    Order getOrderById(Long id);

    Order createOrder(OrderDto orderDto);

    Order updateOrder(OrderDto orderDto);

    void deleteOrder(Long id);
}
