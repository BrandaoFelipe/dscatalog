package com.brandao.dscatalog.tests;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.brandao.dscatalog.dtos.requestDtos.ProductRequestDTO;
import com.brandao.dscatalog.dtos.responseDtos.ProductResponseDTO;
import com.brandao.dscatalog.entities.Category;
import com.brandao.dscatalog.entities.Product;
import com.brandao.dscatalog.mappers.ProductMapper;

public class Factory {
    
    public static Product createProduct(){
        Product product = new Product(2L, "ProductTest", "this product is a awesome test", 800.00, null, Instant.parse("2020-10-10T03:00:00Z"), null, null);
        
        product.setCategories(createNewCategory());

        return product;
    }

    public static ProductRequestDTO createRequestDTO(){

        Product product = createProduct();

        List<Long> categoryIds = product.getCategories().stream().map(Category::getId).collect(Collectors.toList());        

        return new ProductRequestDTO(product.getName(), product.getDescription(), product.getPrice(), product.getImgUrl(), categoryIds);
    }

    public static ProductResponseDTO productResponseDTO(){

        ProductResponseDTO response = ProductMapper.toResponse(createProduct());

        System.out.println("RESPONSE MOCKADO: " + response.getName());

        return response;
    }

    public static Set<Category> createNewCategory(){
        
        Set<Category>categories = new HashSet<>();
        Category category = new Category(1L, "teste");
        Category category2 = new Category(2L, "teste2");

        categories.add(category);
        categories.add(category2);


        return categories;
    }

}