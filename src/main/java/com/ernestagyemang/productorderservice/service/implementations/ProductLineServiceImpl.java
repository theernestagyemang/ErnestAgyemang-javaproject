package com.ernestagyemang.productorderservice.service.implementations;

import com.ernestagyemang.productorderservice.dto.OrderDto;
import com.ernestagyemang.productorderservice.dto.ProductLineDto;
import com.ernestagyemang.productorderservice.model.Order;
import com.ernestagyemang.productorderservice.model.Product;
import com.ernestagyemang.productorderservice.model.ProductLine;
import com.ernestagyemang.productorderservice.repository.ProductLineRepository;
import com.ernestagyemang.productorderservice.service.interfaces.ProductLineService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductLineServiceImpl implements ProductLineService {
    private final ProductLineRepository productLineRepository;
    private final ProductServiceImpl productService;
    private final OrderServiceImpl orderService;

    @Override
    public ProductLine getProductLineById(Long id) {
        return productLineRepository.findById(id).orElseThrow(() -> new RuntimeException("ProductLine not found"));
    }

    @Override
    public ProductLine createProductLine(ProductLineDto ProductLineDto) {
        return productLineRepository.save(ProductLine.builder()
                .product(Product.builder().id(ProductLineDto.getProductId()).build())
                .quantity(ProductLineDto.getQuantity())
                .build());
    }

    @Override
    public List<ProductLine> saveAllProductLines(List<ProductLineDto> productLineDtoList, Order order) {
        List<ProductLine> productLines = new ArrayList<>();

        for (ProductLineDto productLineDto : productLineDtoList) {
            ProductLine productLine = ProductLine.builder()
                    .product(productService.getProductById(productLineDto.getProductId()))
                    .quantity(productLineDto.getQuantity())
                    .order(order)
                    .build();

            productLineRepository.save(productLine);
            productLines.add(productLine);
        }

        return productLines;
    }

    @Override
    public List<ProductLine> updateAllProductLines(List<ProductLineDto> productLineDtoList, Order order) {
        List<ProductLine> productLines = productLineRepository.findAllByOrder(order);

        for (ProductLine productLine : productLines) {
            ProductLineDto updatedProductLineDto = getProductLineDtoById(productLine.getId(), productLineDtoList);
            if (updatedProductLineDto != null) {
                productLine.setQuantity(updatedProductLineDto.getQuantity());
                productLineRepository.save(productLine);
            }
        }
        return productLines;
    }

    private ProductLineDto getProductLineDtoById(Long productLineId, List<ProductLineDto> productLineDtoList) {
        for (ProductLineDto productLineDto : productLineDtoList) {
            if (productLineDto.getId().equals(productLineId)) {
                return productLineDto;
            }
        }
        return null;
    }

    @Override
    public List<Product> getProductsByOrder(OrderDto orderDto) {
        Order order = orderService.getOrderById(orderDto.getId());
        return productLineRepository.findAllByOrder(order).stream().map(ProductLine::getProduct).collect(Collectors.toList());
    }
}
