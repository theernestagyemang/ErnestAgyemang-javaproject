package com.ernestagyemang.productorderservice.service.implementations;

import com.ernestagyemang.productorderservice.dto.ProductDto;
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
        return productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product not found"));
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

    @Override
    public Product createProduct(ProductDto productDto) {
        Product product = Product.builder()
                .name(productDto.getName())
                .stock(productDto.getStock())
                .price(productDto.getPrice())
                .build();
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(ProductDto productDto) {
        Product product = productRepository.findById(productDto.getId()).orElseThrow(() -> new NotFoundException("Product not found"));
        product.setName(productDto.getName());
        product.setStock(productDto.getStock());
        product.setPrice(productDto.getPrice());
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product not found"));
        productRepository.deleteById(id);
    }

}
