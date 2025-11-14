package com.brandao.dscatalog.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.brandao.dscatalog.dtos.request.CategoryRequestDTO;
import com.brandao.dscatalog.dtos.response.CategoryResponseDto;
import com.brandao.dscatalog.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/categories")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> findAll(){

        List<CategoryResponseDto>dtoList = service.findAllCategories();

        return ResponseEntity.ok(dtoList);

    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_OPERATOR')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<CategoryResponseDto> findById(@Valid @PathVariable Long id){

        CategoryResponseDto dto = service.findCategoryByid(id);

        return ResponseEntity.ok(dto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<CategoryResponseDto> updateCategory(@Valid @PathVariable Long id, @RequestBody CategoryRequestDTO dto){

        CategoryResponseDto response = service.updateCategory(dto, id);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
     @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable Long id) {

        service.deleteCategory(id);

        return ResponseEntity.noContent().build();
    }
}
