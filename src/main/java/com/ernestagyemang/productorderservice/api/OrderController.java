package com.ernestagyemang.productorderservice.api;

import com.ernestagyemang.productorderservice.dto.OrderInput;
import com.ernestagyemang.productorderservice.model.Order;
import com.ernestagyemang.productorderservice.service.implementations.OrderServiceImpl;
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

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @QueryMapping
    public List<Order> getAllOrders(Principal principal) {
        return orderService.getAllOrders(principal);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @QueryMapping
    public Order getOrderById(@Argument Long id, Principal principal) {
        return orderService.getOrderById(id, principal);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @QueryMapping
    public List<Order> getAllOrdersByUser(@Argument Long userId, Principal principal) {
        return orderService.getAllOrdersByUser(userId, principal);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @MutationMapping
    public Order createOrder(@Argument OrderInput orderInput, Principal principal) {
        return orderService.createOrder(orderInput, principal);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @MutationMapping
    public Order updateOrder(@Argument OrderInput orderInput, Principal principal) {
        return orderService.updateOrder(orderInput, principal);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @MutationMapping
    public String deleteOrder(@Argument Long id, Principal principal) {
        return orderService.deleteOrder(id, principal);
    }

}
