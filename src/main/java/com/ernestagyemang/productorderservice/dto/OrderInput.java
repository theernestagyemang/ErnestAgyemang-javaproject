package com.ernestagyemang.productorderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderInput {
    private Long id;
    private List<ProductLineInput> productLineInputList;
}
