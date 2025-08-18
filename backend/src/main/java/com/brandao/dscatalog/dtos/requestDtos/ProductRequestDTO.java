package com.brandao.dscatalog.dtos.requestDtos;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class ProductRequestDTO {

    @NotNull(message = "name cannot be null")
    @Size(min = 3, max = 100, message = "name must have betwenn 3 and 100 caracters")
    private String name;

    @NotNull(message = "name cannot be null")
    @Size(min = 3, max = 100, message = "name must have betwenn 3 and 100 caracters")
    private String description;

    @Positive(message = "price must be positive")
    private Double price;
    
    private String imgUrl;

    @Builder.Default
    private List<Long> categories = new ArrayList<>();

}
