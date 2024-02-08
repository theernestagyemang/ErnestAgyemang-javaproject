package com.ernestagyemang.productorderservice.api;

import com.ernestagyemang.productorderservice.dto.OrderInput;
import com.ernestagyemang.productorderservice.model.Order;
import com.ernestagyemang.productorderservice.service.implementations.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;
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
    public List<Order> getAllOrdersByUser(Principal principal) {
        return orderService.getAllOrdersByUser(principal.getName());
    }

    @MutationMapping
    public Order createOrder(@Argument OrderInput orderInput) {
        return orderService.createOrder(orderInput);
    }

    @MutationMapping
    public Order updateOrder(@Argument OrderInput orderInput) {
        return orderService.updateOrder(orderInput);
    }

    @MutationMapping
    public String deleteOrder(@Argument Long id) {
        return orderService.deleteOrder(id);
    }

}
