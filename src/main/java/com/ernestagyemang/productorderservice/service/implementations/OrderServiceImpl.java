package com.ernestagyemang.productorderservice.service.implementations;

import com.ernestagyemang.productorderservice.dto.OrderDto;
import com.ernestagyemang.productorderservice.model.Order;
import com.ernestagyemang.productorderservice.model.ProductLine;
import com.ernestagyemang.productorderservice.repository.OrderRepository;
import com.ernestagyemang.productorderservice.service.interfaces.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserServiceImpl userService;
    private final ProductLineServiceImpl productLineService;

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> getAllOrdersByUser(Long userId) {
        return orderRepository.findAllByUser(userService.getUserById(userId));
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Order createOrder(OrderDto orderDto) {
        // Create Order entity
        Order order = Order.builder()
                .user(userService.getUserById(orderDto.getUserId()))
                .build();
        orderRepository.save(order);

        // Create ProductLines with the Order
        List<ProductLine> productLines = productLineService.saveAllProductLines(orderDto.getProductLinesDto(), order);
        order.setProductLines(productLines); // Associate ProductLines with the Order

        // Save the Order with associated ProductLines
        orderRepository.save(order);

        return order;
    }

    @Override
    @Transactional
    public Order updateOrder(OrderDto orderDto) {
        Order order = orderRepository.findById(orderDto.getId()).orElseThrow(() -> new RuntimeException("Order not found"));
        order.setUser(userService.getUserById(orderDto.getUserId()));
        order.setProductLines(productLineService.updateAllProductLines(orderDto.getProductLinesDto(), order));
        orderRepository.save(order);
        return order;
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        orderRepository.deleteById(id);
    }
}
