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

import com.brandao.dscatalog.dtos.requestDtos.ProductRequestDTO;
import com.brandao.dscatalog.dtos.responseDtos.ProductResponseDTO;
import com.brandao.dscatalog.services.ProductService;

@RestController
@RequestMapping(path = "/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping
    public ResponseEntity<Page<ProductResponseDTO>> findAll(@PageableDefault(page = 0, size = 12, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable){

        Page<ProductResponseDTO>dtoList = service.findAllProducts(pageable);

        return ResponseEntity.ok(dtoList);

    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductResponseDTO> findById(@PathVariable Long id){

        ProductResponseDTO dto = service.findProductById(id);

        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO>newCategory(@RequestBody ProductRequestDTO dto){

        ProductResponseDTO response = service.createNewProduct(dto);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();

        return ResponseEntity.created(uri).body(response);

    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductResponseDTO> updateCategory(@PathVariable Long id, @RequestBody ProductRequestDTO dto){

        ProductResponseDTO response = service.updateProduct(dto, id);

        return ResponseEntity.ok(response);
    }

     @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable Long id) {

        service.deleteProduct(id);

        return ResponseEntity.noContent().build();
    }
}
