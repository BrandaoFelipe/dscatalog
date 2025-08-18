package com.brandao.dscatalog.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.brandao.dscatalog.dtos.requestDtos.CategoryRequestDTO;
import com.brandao.dscatalog.dtos.responseDtos.CategoryResponseDto;
import com.brandao.dscatalog.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/categories")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @GetMapping
    public ResponseEntity<Page<CategoryResponseDto>> findAll(@PageableDefault(page = 0, size = 12, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable){

        Page<CategoryResponseDto>dtoList = service.findAllCategories(pageable);

        return ResponseEntity.ok(dtoList);

    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CategoryResponseDto> findById(@PathVariable Long id){

        CategoryResponseDto dto = service.findCategoryByid(id);

        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDto>newCategory(@Valid @RequestBody CategoryRequestDTO dto){

        CategoryResponseDto response = service.createCategory(dto);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();

        return ResponseEntity.created(uri).body(response);

    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CategoryResponseDto> updateCategory(@PathVariable Long id, @RequestBody CategoryRequestDTO dto){

        CategoryResponseDto response = service.updateCategory(dto, id);

        return ResponseEntity.ok(response);
    }

     @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable Long id) {

        service.deleteCategory(id);

        return ResponseEntity.noContent().build();
    }
}
