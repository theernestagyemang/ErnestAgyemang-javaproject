package com.ernestagyemang.productorderservice.api;

import com.ernestagyemang.productorderservice.dto.OrderInput;
import com.ernestagyemang.productorderservice.dto.ProductInput;
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
    public List<Product> getProductsByOrder(@Argument Long id) {
        return productLineService.getProductsByOrder(id);
    }

    @QueryMapping
    public List<Product> getLowStockProducts(@Argument Integer threshold) {
        return productService.getLowStockProducts(threshold);
    }

    @MutationMapping
    public Product createProduct(@Argument ProductInput productInput) {
        return productService.createProduct(productInput);
    }

    @MutationMapping
    public Product updateProduct(@Argument ProductInput productInput) {
        return productService.updateProduct(productInput);
    }

    @MutationMapping
    public String deleteProduct(@Argument Long id) {
        return productService.deleteProduct(id);
    }
}
