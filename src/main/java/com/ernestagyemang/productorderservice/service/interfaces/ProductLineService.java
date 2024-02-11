package com.ernestagyemang.productorderservice.service.interfaces;

import com.ernestagyemang.productorderservice.dto.ProductLineInput;
import com.ernestagyemang.productorderservice.model.Order;
import com.ernestagyemang.productorderservice.model.Product;
import com.ernestagyemang.productorderservice.model.ProductLine;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public interface ProductLineService {
    List<ProductLine> getProductLinesByOrder(Order order);

    List<ProductLine> saveAllProductLines(List<ProductLineInput> productLineInputList, Order order);

    List<ProductLine> updateAllProductLines(List<ProductLineInput> productLineInputList, Order order);

    List<Product> getProductsByOrder(Long id, Principal principal);

    void deleteProductLine(Long id);
}
