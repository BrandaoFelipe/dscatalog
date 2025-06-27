package com.brandao.dscatalog.mappers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.brandao.dscatalog.dtos.requestDtos.ProductRequestDTO;
import com.brandao.dscatalog.dtos.responseDtos.ProductResponseDTO;
import com.brandao.dscatalog.entities.Category;
import com.brandao.dscatalog.entities.Product;



@Component
public class ProductMapper {

    public static Product toEntity(ProductRequestDTO dto) {
        return Product.builder()
        .name(dto.getName())
        .description(dto.getDescription())
        .price(dto.getPrice())
        .imgUrl(dto.getImgUrl())
        //.categoryList is in service
        .build();
    }

    public static ProductResponseDTO toResponse(Product entity){
        return ProductResponseDTO.builder()
        .id(entity.getId())
        .name(entity.getName())
        .description(entity.getDescription())
        .price(entity.getPrice())
        .categories(mappingSetCategoryObjToStringList(entity))
        .build();
    }

    public static void applyUpdates(ProductRequestDTO dto, Product entity){
        
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setImgUrl(dto.getImgUrl());
    }

    //used to get the category names for the response dto
    private static List<String> mappingSetCategoryObjToStringList(Product entity){

        List<String>list = new ArrayList<>();

        for (Category cat : entity.getCategories()) {
            list.add(cat.getName());
        }

        return list;
    }
}