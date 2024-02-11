package com.ernestagyemang.productorderservice.api;

import com.ernestagyemang.productorderservice.dto.ProductInput;
import com.ernestagyemang.productorderservice.model.Product;
import com.ernestagyemang.productorderservice.service.implementations.ProductLineServiceImpl;
import com.ernestagyemang.productorderservice.service.implementations.ProductServiceImpl;
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
public class ProductController {
    private final ProductServiceImpl productService;
    private final ProductLineServiceImpl productLineService;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @QueryMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @QueryMapping
    public Product getProductById(@Argument Long id) {
        return productService.getProductById(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @QueryMapping
    public List<Product> getProductsByOrder(@Argument Long id, Principal principal) {
        return productLineService.getProductsByOrder(id, principal);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @QueryMapping
    public List<Product> getLowStockProducts(@Argument Integer threshold) {
        return productService.getLowStockProducts(threshold);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @MutationMapping
    public Product createProduct(@Argument ProductInput productInput) {
        return productService.createProduct(productInput);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @MutationMapping
    public Product updateProduct(@Argument ProductInput productInput) {
        return productService.updateProduct(productInput);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @MutationMapping
    public String deleteProduct(@Argument Long id) {
        return productService.deleteProduct(id);
    }
}
