package com.ernestagyemang.productorderservice.service.interfaces;

import com.ernestagyemang.productorderservice.dto.OrderInput;
import com.ernestagyemang.productorderservice.model.Order;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public interface OrderService {
    List<Order> getAllOrders(Principal principal);

    List<Order> getAllOrdersByUser(Long userId, Principal principal);

    Order getOrderById(Long id, Principal principal);

    Order createOrder(OrderInput orderInput, Principal principal);

    Order updateOrder(OrderInput orderInput, Principal principal);

    String deleteOrder(Long id, Principal principal);
}
