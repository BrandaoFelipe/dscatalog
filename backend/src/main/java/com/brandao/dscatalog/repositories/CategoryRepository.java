package com.brandao.dscatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brandao.dscatalog.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

}
