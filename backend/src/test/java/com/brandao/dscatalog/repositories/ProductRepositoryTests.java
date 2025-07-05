package com.brandao.dscatalog.repositories;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.brandao.dscatalog.entities.Product;
import com.brandao.dscatalog.tests.Factory;

@DataJpaTest
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository repository;

    private long inexistentId;
    private long existingId;
    private long countTotalProducts;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1;
        countTotalProducts = 22L;
        inexistentId = 1999L;
    }

    @Test
    public void findByIdShouldReturnProductByIdWhenValidId() {

        Optional<Product> result = repository.findById(existingId);

        Assertions.assertNotNull(result);

    }

    @Test
    public void findByIdShouldNotReturnProductByIdWhenNotValidId() {

        Optional<Product> result = repository.findById(inexistentId);

        Assertions.assertTrue(() -> result.isEmpty());

    }

    @Test
    public void findAllShouldReturnAllProducts(){

        List<Product>result = repository.findAll();

        Assertions.assertFalse(result.isEmpty());
    }

    @Test
    public void deleteShoudDeleteObjectWhenIdExists() {

        repository.deleteById(existingId);

        Optional<Product> result = repository.findById(existingId);

        Assertions.assertFalse(result.isPresent());

    }

    @Test
    public void saveShouldPersistWithAutoIncrementWhenIdIsNull() {

        Product product = Factory.createProduct();
        product.setId(null);

        product = repository.save(product);

        Assertions.assertNotNull(product.getId());
        Assertions.assertEquals(countTotalProducts + 1, product.getId());

    }
}
