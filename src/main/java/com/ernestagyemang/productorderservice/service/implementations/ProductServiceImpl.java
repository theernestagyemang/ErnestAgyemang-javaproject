package com.ernestagyemang.productorderservice.service.implementations;

import com.ernestagyemang.productorderservice.dto.ProductInput;
import com.ernestagyemang.productorderservice.exceptions.NotFoundException;
import com.ernestagyemang.productorderservice.model.Product;
import com.ernestagyemang.productorderservice.repository.ProductRepository;
import com.ernestagyemang.productorderservice.service.interfaces.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product with id " + id + " not found"));
    }

    @Override
    public List<Product> getLowStockProducts(int threshold) {
        List<Product> lowStockProducts = new ArrayList<>();
        List<Product> allProducts = getAllProducts();
        for (Product product : allProducts) {
            if (product.getStock() < threshold) {
                lowStockProducts.add(product);
            }
        }
        return lowStockProducts;
    }

    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public Product createProduct(ProductInput productInput) {
        Product product = Product.builder()
                .name(productInput.getName())
                .stock(productInput.getStock())
                .price(productInput.getPrice())
                .build();
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(ProductInput productInput) {
        Product product = productRepository.findById(productInput.getId()).orElseThrow(() -> new NotFoundException("Product with id " + productInput.getId() + " not found"));
        product.setName(productInput.getName());
        product.setStock(productInput.getStock());
        product.setPrice(productInput.getPrice());
        return productRepository.save(product);
    }

    @Override
    public String deleteProduct(Long id) {
        productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product with id " + id + " not found"));
        productRepository.deleteById(id);
        return "Product with id " + id + " has been deleted";
    }

}
