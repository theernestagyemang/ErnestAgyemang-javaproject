package com.ernestagyemang.productorderservice.service.implementations;

import com.ernestagyemang.productorderservice.dto.OrderInput;
import com.ernestagyemang.productorderservice.dto.ProductLineInput;
import com.ernestagyemang.productorderservice.exceptions.Duplicate409Exception;
import com.ernestagyemang.productorderservice.exceptions.NotFoundException;
import com.ernestagyemang.productorderservice.model.Order;
import com.ernestagyemang.productorderservice.model.ProductLine;
import com.ernestagyemang.productorderservice.model.User;
import com.ernestagyemang.productorderservice.repository.OrderRepository;
import com.ernestagyemang.productorderservice.service.interfaces.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    public List<Order> findAllOrdersByUser(Long userId) {
        return orderRepository.findAllByUser(userService.getUserById(userId));
    }

    @Override
    public List<Order> getAllOrdersByUser(User user) {
        return orderRepository.findAllByUser(user);
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Order with id " + id + " not found"));
    }

    @Override
    @Transactional
    public Order createOrder(OrderInput orderInput) {
        // Create Order entity
        Order order = Order.builder()
                .user(userService.getUserById(orderInput.getUserId()))
                .build();
        orderRepository.save(order);

        // Check for duplicate product IDs in the ProductLines
        Set<Long> productIds = new HashSet<>();
        for (ProductLineInput productLineInput : orderInput.getProductLineInputList()) {
            if (!productIds.add(productLineInput.getProductId())) {
                // Duplicate product ID found
                throw new Duplicate409Exception("Duplicate product ID in the productLines: " + productLineInput.getProductId());
            }
        }

        // Create ProductLines with the Order
        List<ProductLine> productLines = productLineService.saveAllProductLines(orderInput.getProductLineInputList(), order);
        order.setProductLineList(productLines); // Associate ProductLines with the Order

        // Save the Order with associated ProductLines
        orderRepository.save(order);

        return order;
    }

    @Override
    @Transactional
    public Order updateOrder(OrderInput orderInput) {
        Order order = orderRepository.findById(orderInput.getId()).orElseThrow(() -> new NotFoundException("Order with id " + orderInput.getId() + " not found"));
        order.setUser(userService.getUserById(orderInput.getUserId()));
        //Check this service method for Business Logic
        order.setProductLineList(productLineService.updateAllProductLines(orderInput.getProductLineInputList(), order));
        orderRepository.save(order);
        return order;
    }

    @Override
    @Transactional
    public String deleteOrder(Long id) {
        productLineService.getProductLinesByOrder(orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Order not with id " + id + " not found")))
                .forEach(productLine -> productLineService.deleteProductLine(productLine.getId()));
        orderRepository.deleteById(id);
        return "Order with id " + id + " has been deleted";
    }
}
