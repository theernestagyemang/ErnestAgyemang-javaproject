package com.ernestagyemang.productorderservice.service.interfaces;

import com.ernestagyemang.productorderservice.dto.ProductInput;
import com.ernestagyemang.productorderservice.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    List<Product> getAllProducts();

    Product getProductById(Long id);

    List<Product> getLowStockProducts(int threshold);

    Product createProduct(ProductInput productInput);

    Product updateProduct(ProductInput productInput);

    String deleteProduct(Long id);
}
