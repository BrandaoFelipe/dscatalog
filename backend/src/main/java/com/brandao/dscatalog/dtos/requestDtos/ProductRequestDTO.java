package com.brandao.dscatalog.dtos.requestDtos;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class ProductRequestDTO {

    private String name;
    private String description;
    private Double price;
    private String imgUrl;

    @Builder.Default
    private List<Long> categories = new ArrayList<>();

}
