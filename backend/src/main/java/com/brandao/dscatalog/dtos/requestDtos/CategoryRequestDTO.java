package com.brandao.dscatalog.dtos.requestDtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class CategoryRequestDTO {

    private String name;

}
