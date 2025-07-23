package com.brandao.dscatalog.dtos.responseDtos;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class ProductResponseDTO {

    private Long id;
    private String name;
    private String description;
    private Double price;

    @Builder.Default
    private List<String> categories = new ArrayList<>();    

}
