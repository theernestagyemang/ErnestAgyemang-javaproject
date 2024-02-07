package com.ernestagyemang.productorderservice.api;

import com.ernestagyemang.productorderservice.dto.OrderDto;
import com.ernestagyemang.productorderservice.dto.ProductDto;
import com.ernestagyemang.productorderservice.model.Product;
import com.ernestagyemang.productorderservice.service.implementations.ProductLineServiceImpl;
import com.ernestagyemang.productorderservice.service.implementations.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductServiceImpl productService;
    private final ProductLineServiceImpl productLineService;

    @QueryMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @QueryMapping
    public Product getProductById(@Argument Long id) {
        return productService.getProductById(id);
    }

    @QueryMapping
    public List<Product> getProductsByOrder(@Argument OrderDto orderDto) {
        return productLineService.getProductsByOrder(orderDto);
    }

    @QueryMapping
    public List<Product> getLowStockProducts(@Argument int threshold) {
        return productService.getLowStockProducts(threshold);
    }

    @MutationMapping
    public Product createProduct(@Argument ProductDto productDto) {
        return productService.createProduct(productDto);
    }

    @MutationMapping
    public Product updateProduct(@Argument ProductDto productDto) {
        return productService.updateProduct(productDto);
    }

    @MutationMapping
    public void deleteProduct(@Argument Long id) {
        productService.deleteProduct(id);
    }
}
