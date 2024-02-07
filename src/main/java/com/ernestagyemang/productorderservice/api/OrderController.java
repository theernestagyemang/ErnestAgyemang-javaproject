package com.ernestagyemang.productorderservice.api;

import com.ernestagyemang.productorderservice.dto.OrderDto;
import com.ernestagyemang.productorderservice.model.Order;
import com.ernestagyemang.productorderservice.service.implementations.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderServiceImpl orderService;

    @QueryMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @QueryMapping
    public Order getOrderById(@Argument Long id) {
        return orderService.getOrderById(id);
    }

    @QueryMapping
    public List<Order> getAllOrdersByUser(@Argument Long userId) {
        return orderService.getAllOrdersByUser(userId);
    }

    @MutationMapping
    public Order createOrder(@Argument OrderDto orderDto) {
        return orderService.createOrder(orderDto);
    }

    @MutationMapping
    public Order updateOrder(@Argument OrderDto orderDto) {
        return orderService.updateOrder(orderDto);
    }

    @MutationMapping
    public void deleteOrder(@Argument Long id) {
        orderService.deleteOrder(id);
    }

}
