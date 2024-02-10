package com.ernestagyemang.productorderservice.api;

import com.ernestagyemang.productorderservice.dto.OrderInput;
import com.ernestagyemang.productorderservice.model.Order;
import com.ernestagyemang.productorderservice.service.implementations.OrderServiceImpl;
import com.ernestagyemang.productorderservice.service.implementations.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderServiceImpl orderService;
    private final UserServiceImpl userService;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @QueryMapping
    public List<Order> getAllOrders(Principal principal) {
        return orderService.getAllOrders();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @QueryMapping
    public Order getOrderById(@Argument Long id) {
        return orderService.getOrderById(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @QueryMapping
    public List<Order> getAllOrdersByUser(@Argument Long userId, Principal principal) {
        return orderService.getAllOrdersByUser(userId, principal);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @MutationMapping
    public Order createOrder(@Argument OrderInput orderInput) {
        return orderService.createOrder(orderInput);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @MutationMapping
    public Order updateOrder(@Argument OrderInput orderInput) {
        return orderService.updateOrder(orderInput);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @MutationMapping
    public String deleteOrder(@Argument Long id) {
        return orderService.deleteOrder(id);
    }

}
