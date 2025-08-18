package com.brandao.dscatalog.dtos.requestDtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class CategoryRequestDTO {

    @NotNull(message = "name cannot be null")
    @Size(min = 3, max = 150)
    private String name;

}
