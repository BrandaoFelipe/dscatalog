package com.brandao.dscatalog.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.brandao.dscatalog.dtos.responseDtos.ProductResponseDTO;
import com.brandao.dscatalog.repositories.ProductRepository;
import com.brandao.dscatalog.services.exceptions.NotFoundException;

@SpringBootTest
public class ProductServiceIntegrationTests {

    @Autowired
    private ProductService service;

    @Autowired
    private ProductRepository repository;

    private long existingId;
    private long nonExistingId;
    private long productTotalCount;

    @BeforeEach
    void setUp() throws Exception {

        existingId = 1L;
        nonExistingId = 999L;
        productTotalCount = 22L;
    }

    @Test
    public void deleteShouldDeleteResourceWhenIdExists() throws Exception {

        service.deleteProduct(existingId);

        Assertions.assertEquals(productTotalCount - 1, repository.count());
    }

    @Test
    public void deleteShouldThrowNotFoundExceptionWhenNonExistingId() throws Exception {

        Assertions.assertThrows(NotFoundException.class, () -> {
            service.deleteProduct(nonExistingId);
        });

        Assertions.assertEquals(productTotalCount, repository.count());
    }

    @Test
    public void findAllShouldReturnPage0Size12() throws Exception {

        PageRequest pageRequest = PageRequest.of(0, 12); // a valid object to simulate the request

        Page<ProductResponseDTO> result = service.findAllProducts(pageRequest); // make the request and store the result
                                                                                // in the Page result object

        assertFalse(result.isEmpty()); // if the object is not empty it should return "true"
        assertEquals(0, result.getNumber()); // check if the number of the page is zero
        assertEquals(12, result.getSize()); // check if the size of the page is 12;
        assertEquals(productTotalCount, result.getTotalElements());
    }

    @Test
    public void findAllShouldReturnEmptyPageWhenPageDoesNotExists() throws Exception {

        PageRequest pageRequest = PageRequest.of(50, 12); // an invalid object to simulate a failed request

        Page<ProductResponseDTO> result = service.findAllProducts(pageRequest);

        assertTrue(result.isEmpty()); // if the object is empty it should return true

    }

    @Test
    public void findAllShouldReturnPage0Size12SortedByName() throws Exception {

        PageRequest pageRequest = PageRequest.of(0, 12, Sort.by("name"));

        Page<ProductResponseDTO> result = service.findAllProducts(pageRequest);

        assertFalse(result.isEmpty());
        assertEquals("Adaga Goblin", result.getContent().get(0).getName());
        assertEquals("Amuleto da Estrela da Noite (Arwen)", result.getContent().get(1).getName());
        assertEquals("Arco de Legolas", result.getContent().get(2).getName());

    }

}
