package com.ernestagyemang.productorderservice.service.interfaces;

import com.ernestagyemang.productorderservice.dto.ProductDto;
import com.ernestagyemang.productorderservice.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    List<Product> getAllProducts();

    Product getProductById(Long id);

    List<Product> getLowStockProducts(int threshold);

    Product createProduct(ProductDto productDto);

    Product updateProduct(ProductDto productDto);

    void deleteProduct(Long id);
}
