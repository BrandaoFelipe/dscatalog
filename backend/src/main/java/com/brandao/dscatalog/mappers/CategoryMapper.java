package com.brandao.dscatalog.mappers;

import org.springframework.stereotype.Component;

import com.brandao.dscatalog.dtos.request.CategoryRequestDTO;
import com.brandao.dscatalog.dtos.response.CategoryResponseDto;
import com.brandao.dscatalog.entities.Category;

@Component
public class CategoryMapper {

    public static Category toEntity(CategoryRequestDTO dto) {
        return Category.builder()
        .name(dto.getName())
        .build();
    }

    public static CategoryResponseDto toResponse(Category entity){
        return CategoryResponseDto.builder()
        .id(entity.getId())
        .name(entity.getName())
        .build();
    }

    public static void applyUpdates(CategoryRequestDTO dto, Category entity){
        entity.setName(dto.getName());
        
    }

}