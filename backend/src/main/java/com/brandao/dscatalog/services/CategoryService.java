package com.brandao.dscatalog.services;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brandao.dscatalog.dtos.request.CategoryRequestDTO;
import com.brandao.dscatalog.dtos.response.CategoryResponseDto;
import com.brandao.dscatalog.entities.Category;
import com.brandao.dscatalog.mappers.CategoryMapper;
import com.brandao.dscatalog.repositories.CategoryRepository;
import com.brandao.dscatalog.services.exceptions.NotFoundException;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Transactional(readOnly = true)
    public List<CategoryResponseDto> findAllCategories() {

        List<Category> list = repository.findAll();

        List<CategoryResponseDto> dto = list.stream().map(CategoryMapper::toResponse).toList();

        return dto;
    }

    @Transactional(readOnly = true)
    public CategoryResponseDto findCategoryByid(Long id) {

        Category entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("id not found"));

        CategoryResponseDto dto = CategoryMapper.toResponse(entity);

        return dto;
    }

    @Transactional
    public CategoryResponseDto createCategory(CategoryRequestDTO dto) {

        Objects.requireNonNull(dto, "Request cannot be null");

        Category entity = CategoryMapper.toEntity(dto);

        repository.save(entity);

        return CategoryMapper.toResponse(entity);
    }

    @Transactional
    public CategoryResponseDto updateCategory(CategoryRequestDTO dto, Long id) {

        Category entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("id not found"));

        CategoryMapper.applyUpdates(dto, entity);

        repository.save(entity);

        return CategoryMapper.toResponse(entity);
    }

    @Transactional
    public void deleteCategory(Long id) {

        if (!repository.existsById(id))
            throw new NotFoundException("id not found");

        repository.deleteById(id);
    }

    //This method was created to get the category data for the product persistance.
    //It's not to expose this entity at an endpoint
    @Transactional(readOnly = true)
    public Category findCategoryEntityByIdForInternalUseOnly(Long id) {

        Category entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("id not found"));

        return entity;
    }
}
