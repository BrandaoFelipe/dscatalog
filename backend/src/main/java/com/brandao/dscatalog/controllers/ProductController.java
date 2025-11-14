package com.brandao.dscatalog.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.brandao.dscatalog.dtos.request.ProductRequestDTO;
import com.brandao.dscatalog.dtos.response.ProductResponseDTO;
import com.brandao.dscatalog.services.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping
    public ResponseEntity<Page<ProductResponseDTO>> findAll(Pageable pageable) {

        Page<ProductResponseDTO> dtoList = service.findAll(pageable);

        return ResponseEntity.ok(dtoList);

    }

    @GetMapping("/search")
    public ResponseEntity<Page<ProductResponseDTO>> findAllQuery(Pageable pageable,
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "categoryIds", defaultValue = "") List<Long> categoryIds) {

        Page<ProductResponseDTO> dtoList = service.findAllProducts(pageable, name, categoryIds);

        return ResponseEntity.ok(dtoList);

    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_OPERATOR')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductResponseDTO> findById(@Valid @PathVariable Long id) {

        ProductResponseDTO dto = service.findProductById(id);

        return ResponseEntity.ok(dto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<ProductResponseDTO> newProduct(@Valid @RequestBody ProductRequestDTO dto) {

        ProductResponseDTO response = service.createNewProduct(dto);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();

        return ResponseEntity.created(uri).body(response);

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@Valid @PathVariable Long id,
            @RequestBody ProductRequestDTO dto) {

        ProductResponseDTO response = service.updateProduct(dto, id);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteProduct(@Valid @PathVariable Long id) {

        service.deleteProduct(id);

        return ResponseEntity.noContent().build();
    }
}
