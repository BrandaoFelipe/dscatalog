package com.brandao.dscatalog.services;

import static org.mockito.Mockito.times;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.brandao.dscatalog.dtos.request.ProductRequestDTO;
import com.brandao.dscatalog.dtos.response.ProductResponseDTO;
import com.brandao.dscatalog.entities.Category;
import com.brandao.dscatalog.entities.Product;
import com.brandao.dscatalog.repositories.ProductRepository;
import com.brandao.dscatalog.services.exceptions.DatabaseException;
import com.brandao.dscatalog.services.exceptions.NotFoundException;
import com.brandao.dscatalog.tests.Factory;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

    @InjectMocks //injeta tudo que tiver @Mock dentro do ProductService
    private ProductService service;

    @Mock
    private CategoryService catService;

    @Mock
    private ProductRepository repository;   

    private long existingId;
    private long nonExistingId;
    private long dependantId;
    private Product product;
    private Set<Category> categories;
    private ProductRequestDTO requestDto;
    private PageImpl<Product> page; //essa instancia PAGEIMPL é usada para tests

    @BeforeEach
    void setUp() throws Exception {        
        nonExistingId = 1L;
        existingId = 2L;
        dependantId = 3L;
        product = Factory.createProduct();
        categories = Factory.createNewCategory();
        requestDto = Factory.createRequestDTO();
        page = new PageImpl<>(List.of(product));

        Mockito.when(repository.existsById(nonExistingId)).thenReturn(false); // garantir que Id será falso
        Mockito.when(repository.existsById(existingId)).thenReturn(true); // garantir que Id será verdadeiro
        Mockito.when(repository.existsById(dependantId)).thenReturn(true);// u know the drill

        //deleteShouldDoNothingWhenIdExists
        Mockito.doNothing().when(repository).deleteById(existingId); // não lança exceção pq id existe

        //deleteShoudThrowDatabaseExceptionWhenDependentId
        Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependantId);

        //findAllShouldReturnPage
        Mockito.when(repository.findAll((Pageable)ArgumentMatchers.any())).thenReturn(page); //(pageable) findall pede casting

        //createNewProductShouldCreateProductWhenProductRequestDtoIsValid
        Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(product); //ArgumentMatchers.any() p qlqr argumento
        //createNewProductShouldCreateProductWhenProductRequestDtoIsValid auxiliarMethod
       for (Category cat : categories) {
         Mockito.when(catService.findCategoryEntityByIdForInternalUseOnly(cat.getId())).thenReturn(cat);
       }
        //findByIdShouldReturnProductResponseDtoWhenExistingId
        Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(product));

        //findByIdShouldThrowNotFoundExceptionWhenNonExistingId
        Mockito.when(repository.findById(nonExistingId)).thenReturn(Optional.empty());

    }

    @Test
    public void deleteShouldDoNothingWhenIdExists() {

        Assertions.assertDoesNotThrow(() -> {
            service.deleteProduct(existingId);
        });

        Mockito.verify(repository, Mockito.times(1)).deleteById(existingId);
    }

    @Test
    public void deleteShouldThrowNotFoundExceptionWhenIdNotExists() {

        Assertions.assertThrows(NotFoundException.class, () -> {
            service.deleteProduct(nonExistingId);
        });

        Mockito.verify(repository, Mockito.times(0)).deleteById(nonExistingId);
    }

    @Test
    public void deleteShoudThrowDatabaseExceptionWhenDependentId() {

        Assertions.assertThrows(DatabaseException.class, () -> {
            service.deleteProduct(dependantId);
        });

        Mockito.verify(repository, times(1)).deleteById(dependantId);
    }

    @Test
    public void findAllShouldReturnPage(){
        
        Pageable pageable = PageRequest.of(0, 10);

        Page<ProductResponseDTO>result = service.findAll(pageable);

        Assertions.assertNotNull(result);

        Mockito.verify(repository, times(1)).findAll(pageable);
    }

    @Test
    public void findByIdShouldReturnProductResponseDtoWhenExistingId(){

        ProductResponseDTO result = service.findProductById(existingId);
        
        Assertions.assertNotNull(result);

        Mockito.verify(repository, times(1)).findById(existingId);
    }

    @Test
    public void findByIdShouldThrowNotFoundExceptionWhenNonExistingId(){
        
        Assertions.assertThrows(NotFoundException.class, () -> {
            service.findProductById(nonExistingId);
        });
        Mockito.verify(repository, times(1)).findById(nonExistingId);
    }

    @Test
    public void createNewProductShouldCreateProductWhenProductRequestDtoIsValid(){        

        ProductResponseDTO result = service.createNewProduct(requestDto);

        Assertions.assertNotNull(result);

        Mockito.verify(repository, times(1)).save(ArgumentMatchers.any());
        Mockito.verify(catService, times(categories.size())).findCategoryEntityByIdForInternalUseOnly(ArgumentMatchers.any());
    }

    @Test
    public void updateProductShouldUpdateProductWhenIdAndProductRequestDtoIsValid(){

        ProductResponseDTO result = service.updateProduct(requestDto, existingId);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(existingId, result.getId());

        Mockito.verify(repository, times(1)).save(ArgumentMatchers.any());
        Mockito.verify(catService, times(categories.size())).findCategoryEntityByIdForInternalUseOnly(ArgumentMatchers.any());

    }
}