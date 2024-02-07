package com.ernestagyemang.productorderservice.service.interfaces;

import com.ernestagyemang.productorderservice.dto.OrderDto;
import com.ernestagyemang.productorderservice.dto.ProductLineDto;
import com.ernestagyemang.productorderservice.model.Order;
import com.ernestagyemang.productorderservice.model.Product;
import com.ernestagyemang.productorderservice.model.ProductLine;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductLineService {
    ProductLine getProductLineById(Long id);

    ProductLine createProductLine(ProductLineDto productLineDto);

    List<ProductLine> saveAllProductLines(List<ProductLineDto> productLineDtoList, Order order);

    List<ProductLine> updateAllProductLines(List<ProductLineDto> productLineDtoList, Order order);

    List<Product> getProductsByOrder(OrderDto orderDto);
}
