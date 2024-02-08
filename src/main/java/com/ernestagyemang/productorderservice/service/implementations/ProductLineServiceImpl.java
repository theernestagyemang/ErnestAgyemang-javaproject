package com.ernestagyemang.productorderservice.service.implementations;

import com.ernestagyemang.productorderservice.dto.OrderInput;
import com.ernestagyemang.productorderservice.dto.ProductLineInput;
import com.ernestagyemang.productorderservice.exceptions.LowStockException;
import com.ernestagyemang.productorderservice.exceptions.NotFoundException;
import com.ernestagyemang.productorderservice.model.Order;
import com.ernestagyemang.productorderservice.model.Product;
import com.ernestagyemang.productorderservice.model.ProductLine;
import com.ernestagyemang.productorderservice.repository.OrderRepository;
import com.ernestagyemang.productorderservice.repository.ProductLineRepository;
import com.ernestagyemang.productorderservice.service.interfaces.ProductLineService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductLineServiceImpl implements ProductLineService {
    private final ProductLineRepository productLineRepository;
    private final OrderRepository orderRepository;
    private final ProductServiceImpl productService;


    @Override
    public List<ProductLine> getProductLinesByOrder(Order order) {
        return productLineRepository.findAllByOrder(order);
    }

    @Override
    public List<Product> getProductsByOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Order with id " + id + " not found"));
        return productLineRepository.findAllByOrder(order).stream().map(ProductLine::getProduct).collect(Collectors.toList());
    }


    @Override
    @Transactional
    public List<ProductLine> saveAllProductLines(List<ProductLineInput> productLineInputList, Order order) {
        List<ProductLine> productLines = new ArrayList<>();

        productLineInputList.forEach(productLineInput -> {
            Product product = productService.getProductById(productLineInput.getProductId());
            // Check if stock is sufficient
            if (product.getStock() - productLineInput.getQuantity() < 0) {
                throw new LowStockException("Insufficient stock for product with id " + product.getId());
            }
            ProductLine productLine = ProductLine.builder()
                    .product(product)
                    .quantity(productLineInput.getQuantity())
                    .order(order)
                    .build();

            //Reduce stock of product
            product.setStock(productService.getProductById(productLineInput.getProductId()).getStock() - productLineInput.getQuantity());
            productService.saveProduct(product);

            productLines.add(productLine);
        });
        return productLineRepository.saveAll(productLines);
    }

    @Override
    @Transactional
    public List<ProductLine> updateAllProductLines(List<ProductLineInput> productLineInputList, Order order) {
        List<ProductLine> existingProductLines = productLineRepository.findAllByOrder(order);
        List<ProductLine> updatedProductLines = new ArrayList<>();

        // Loop through existing ProductLines
        existingProductLines.forEach(productLine -> {
            ProductLineInput updatedProductLineInput = getProductLineInputById(productLine.getId(), productLineInputList);
            if (updatedProductLineInput != null) {
                Product product = productService.getProductById(productLine.getProduct().getId());
                // Update product stock
                if (product.getStock() - updatedProductLineInput.getQuantity() < 0) {
                    throw new LowStockException("Insufficient stock for product with id " + product.getId());
                }
                // Update product stock
                product.setStock(product.getStock() + productLine.getQuantity() - updatedProductLineInput.getQuantity());
                productService.saveProduct(product);
                // Update existing ProductLine's quantity
                productLine.setQuantity(updatedProductLineInput.getQuantity());
                updatedProductLines.add(productLine);
            } else {
                // ProductLine exists in the order but not in the updated list, set quantity to 0
                Product product = productService.getProductById(productLine.getProduct().getId());
                product.setStock(product.getStock() + productLine.getQuantity());
                productService.saveProduct(product);
                productLine.setQuantity(0);
                updatedProductLines.add(productLine);
            }
        });

        // Loop through new ProductLines
        productLineInputList.forEach(productLineInput -> {
            if (getProductLineById(productLineInput.getId(), existingProductLines) == null) {
                // New product line, create a new ProductLine
                Product product = productService.getProductById(productLineInput.getProductId());
                // Check if stock is sufficient
                if (product.getStock() - productLineInput.getQuantity() < 0) {
                    throw new LowStockException("Insufficient stock for product with id " + product.getId());
                }
                // Update product stock
                product.setStock(product.getStock() - productLineInput.getQuantity());
                productService.saveProduct(product);
                ProductLine newProductLine = ProductLine.builder()
                        .product(product)
                        .quantity(productLineInput.getQuantity())
                        .order(order)
                        .build();
                productLineRepository.save(newProductLine);
                updatedProductLines.add(newProductLine);
            }
        });

        return updatedProductLines;
    }

    private ProductLineInput getProductLineInputById(Long productLineId, List<ProductLineInput> productLineInputList) {
        return productLineInputList.stream()
                .filter(pl -> pl.getId().equals(productLineId))
                .findFirst()
                .orElse(null);
    }

    // Helper method to get ProductLine by ID
    private ProductLine getProductLineById(Long productLineId, List<ProductLine> productLines) {
        return productLines.stream()
                .filter(pl -> pl.getId().equals(productLineId))
                .findFirst()
                .orElse(null);
    }


    @Override
    public void deleteProductLine(Long id) {
        productLineRepository.findById(id).orElseThrow(() -> new NotFoundException("ProductLine with id " + id + " not found"));
        productLineRepository.deleteById(id);
    }

    private ProductLineInput getProductLineDtoById(Long productLineId, List<ProductLineInput> productLineInputList) {
        for (ProductLineInput productLineInput : productLineInputList) {
            if (productLineInput.getId().equals(productLineId)) {
                return productLineInput;
            }
        }
        throw new NotFoundException("ProductLine with id " + productLineId + " not found");
    }
}
