package com.ernestagyemang.productorderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductLineDto {
    private Long id;
    private Integer quantity;
    private Long productId;
    private Long orderId;
}
