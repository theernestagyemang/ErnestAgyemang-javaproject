package com.ernestagyemang.productorderservice.service.implementations;

import com.ernestagyemang.productorderservice.dto.OrderInput;
import com.ernestagyemang.productorderservice.dto.ProductLineInput;
import com.ernestagyemang.productorderservice.enums.UserRole;
import com.ernestagyemang.productorderservice.exceptions.Duplicate409Exception;
import com.ernestagyemang.productorderservice.exceptions.NotAuthorizedException;
import com.ernestagyemang.productorderservice.exceptions.NotFoundException;
import com.ernestagyemang.productorderservice.model.Order;
import com.ernestagyemang.productorderservice.model.ProductLine;
import com.ernestagyemang.productorderservice.repository.OrderRepository;
import com.ernestagyemang.productorderservice.service.interfaces.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserServiceImpl userService;
    private final ProductLineServiceImpl productLineService;

    @Override
    public List<Order> getAllOrders(Principal principal) {
        if (userService.currentUser(principal).getRole() == UserRole.USER) {
            return orderRepository.findAllByUser(userService.currentUser(principal));
        }
        return orderRepository.findAll();
    }

    @Override
    public List<Order> getAllOrdersByUser(Long userId, Principal principal) {
        if (userService.currentUser(principal).getRole() == UserRole.USER) {
            if (!Objects.equals(userService.currentUser(principal).getId(), userId)) {
                throw new NotAuthorizedException("You do not  have permission to see this order with id "
                        + userId);
            }
        }
        return orderRepository.findAllByUser(userService.getUserById(userId));
    }

    @Override
    public Order getOrderById(Long id, Principal principal) {
        Order order = orderRepository.findById(id).orElseThrow(()
                -> new NotFoundException("Order with id " + id + " not found"));
        if (userService.currentUser(principal).getRole() == UserRole.USER) {
            if (!Objects.equals(userService.currentUser(principal).getId(), order.getUser().getId())) {
                throw new NotAuthorizedException("You did not make this order with id " + id
                        + " and do not  have permission to this order");
            }
        }
        return order;
    }

    @Override
    @Transactional
    public Order createOrder(OrderInput orderInput, Principal principal) {
        // Create Order entity
        Order order = Order.builder()
                .user(userService.currentUser(principal))
                .build();
        orderRepository.save(order);

        // Check for duplicate product IDs in the ProductLines
        Set<Long> productIds = new HashSet<>();
        for (ProductLineInput productLineInput : orderInput.getProductLineInputList()) {
            if (!productIds.add(productLineInput.getProductId())) {
                // Duplicate product ID found
                throw new Duplicate409Exception("Duplicate product ID in the productLines: "
                        + productLineInput.getProductId());
            }
        }

        // Create ProductLines with the Order
        List<ProductLine> productLines =
                productLineService.saveAllProductLines(orderInput.getProductLineInputList(), order);
        order.setProductLineList(productLines); // Associate ProductLines with the Order

        // Save the Order with associated ProductLines
        orderRepository.save(order);

        return order;
    }

    @Override
    @Transactional
    public Order updateOrder(OrderInput orderInput, Principal principal) {
        Order order = orderRepository.findById(orderInput.getId()).orElseThrow(()
                -> new NotFoundException("Order with id " + orderInput.getId() + " not found"));
        if (!Objects.equals(userService.currentUser(principal).getId(), order.getUser().getId())) {
            throw new NotAuthorizedException("You cannot update an order for another user. " +
                    "Use your user id =>" + userService.currentUser(principal).getId());
        }
        getOrderById(order.getId(), principal);
        order.setProductLineList(
                productLineService.updateAllProductLines(orderInput.getProductLineInputList(), order));
        orderRepository.save(order);
        return order;
    }

    @Override
    @Transactional
    public String deleteOrder(Long id, Principal principal) {
        getOrderById(id, principal);
        productLineService.getProductLinesByOrder(orderRepository.findById(id).orElseThrow(()
                        -> new NotFoundException("Order not with id " + id + " not found")))
                .forEach(productLine -> productLineService.deleteProductLine(productLine.getId()));
        orderRepository.deleteById(id);
        return "Order with id " + id + " has been deleted";
    }
}
