package com.brandao.dscatalog.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brandao.dscatalog.dtos.requestDtos.ProductRequestDTO;
import com.brandao.dscatalog.dtos.responseDtos.ProductResponseDTO;
import com.brandao.dscatalog.entities.Category;
import com.brandao.dscatalog.entities.Product;
import com.brandao.dscatalog.mappers.ProductMapper;
import com.brandao.dscatalog.repositories.ProductRepository;
import com.brandao.dscatalog.services.exceptions.DatabaseException;
import com.brandao.dscatalog.services.exceptions.EmptyRequestException;
import com.brandao.dscatalog.services.exceptions.NotFoundException;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private CategoryService categoryService;

    @Transactional(readOnly = true)
    public Page<ProductResponseDTO> findAllProducts(Pageable pageable) {

        Page<Product> list = repository.findAll(pageable);

        Page<ProductResponseDTO> dtoPage = list.map(ProductMapper::toResponse);

        return dtoPage;
    }

    @Transactional(readOnly = true)
    public ProductResponseDTO findProductById(Long id) {

        Product product = repository.findById(id).orElseThrow(() -> new NotFoundException("item not found"));

        ProductResponseDTO dto = ProductMapper.toResponse(product);

        return dto;
    }

    @Transactional
    public ProductResponseDTO createNewProduct(ProductRequestDTO request) {

        if (request == null)
            throw new EmptyRequestException("object cannot be null");

        Product entity = ProductMapper.toEntity(request);
        entity.setCategories(categoriesForProductsEntities(request));

        repository.save(entity);

        ProductResponseDTO response = ProductMapper.toResponse(entity);

        return response;

    }

    @Transactional
    public ProductResponseDTO updateProduct(ProductRequestDTO request, Long id) {

        Product entity = repository.findById(id).orElseThrow(() -> new NotFoundException("item not found"));

        ProductMapper.applyUpdates(request, entity);
        entity.setCategories(categoriesForProductsEntities(request));

        repository.save(entity);

        ProductResponseDTO response = ProductMapper.toResponse(entity);

        return response;

    }

    @Transactional
    public void deleteProduct(Long id) {

        if (!repository.existsById(id))
            throw new NotFoundException("id not found");

        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Cannot delete entity: it's related to other data.");
        }
    }

    private Set<Category> categoriesForProductsEntities(ProductRequestDTO dto) {

        Set<Category> categories = new HashSet<>();

        for (Long num : dto.getCategories()) {

            Category cat = categoryService.findCategoryEntityByIdForInternalUseOnly(num);

            categories.add(cat);
        }

        return categories;
    }

}
