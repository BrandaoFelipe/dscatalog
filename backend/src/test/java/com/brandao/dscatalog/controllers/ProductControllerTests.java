package com.brandao.dscatalog.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.brandao.dscatalog.dtos.requestDtos.ProductRequestDTO;
import com.brandao.dscatalog.dtos.responseDtos.ProductResponseDTO;
import com.brandao.dscatalog.services.ProductService;
import com.brandao.dscatalog.services.exceptions.DatabaseException;
import com.brandao.dscatalog.services.exceptions.NotFoundException;
import com.brandao.dscatalog.tests.Factory;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ProductController.class)
public class ProductControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService service;

    @Autowired
    ObjectMapper objMap;

    private long existingId;
    private long nonExistingId;
    private long dependantId;
    private ProductResponseDTO productResponseDTO;
    private PageImpl<ProductResponseDTO> page;
    private ProductRequestDTO productRequestDTO;

    @BeforeEach
    void setUp() throws Exception {
        dependantId = 3L;
        existingId = 2L;
        nonExistingId = 1L;
        productResponseDTO = Factory.productResponseDTO();
        page = new PageImpl<>(List.of(productResponseDTO));
        productRequestDTO = Factory.createRequestDTO();

        // findAllProductsShouldReturnPage
        when(service.findAllProducts(any())).thenReturn(page);

        // findProductByIdShouldReturnProductWhenValidId
        when(service.findProductById(existingId)).thenReturn(productResponseDTO);

        // findProductByIdShouldReturnNotFoundWhenInvalidId
        when(service.findProductById(nonExistingId)).thenThrow(NotFoundException.class);

        // updateProductShouldUpdateProductWhenValidId
        when(service.updateProduct(any(), eq(existingId))).thenReturn(productResponseDTO);

        // updateProductShouldThrowNotFoundExeptionWhenInvalidId
        when(service.updateProduct(any(), eq(nonExistingId))).thenThrow(NotFoundException.class);

        // deleteProductShouldReturnNoContentWhenValidId
        doNothing().when(service).deleteProduct(existingId); // << when the method returns null, that's the sintaxe

        // deleteProductShouldThrowNotFoundExceptionWhenInvalidId
        doThrow(NotFoundException.class).when(service).deleteProduct(nonExistingId);

        // deleteProductShouldThrowBadRequestExceptionWhenDependantId
        doThrow(DatabaseException.class).when(service).deleteProduct(dependantId);

        //createProductShouldCreateProduct
        when(service.createNewProduct(any())).thenReturn(productResponseDTO);

    }

    @Test
    public void findAllProductsShouldReturnPage() throws Exception {

        ResultActions result = mockMvc.perform(get("/products")
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
    }

    @Test
    public void findProductByIdShouldReturnProductWhenValidId() throws Exception {

        ResultActions result = mockMvc.perform(get("/products/{id}", existingId)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.name").exists());
        result.andExpect(jsonPath("$.description").exists());
        result.andExpect(jsonPath("$.price").exists());
    }

    @Test
    public void findProductByIdShouldReturnNotFoundWhenInvalidId() throws Exception {

        ResultActions result = mockMvc.perform(get("/products/{id}", nonExistingId)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }

    @Test
    public void createProductShouldCreateProduct() throws Exception {

        String jsonBody = objMap.writeValueAsString(productRequestDTO);

        ResultActions result = mockMvc.perform(post("/products")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.name").exists());
        result.andExpect(jsonPath("$.description").exists());
        result.andExpect(jsonPath("$.price").exists());
    }

    @Test
    public void updateProductShouldUpdateProductWhenValidId() throws Exception {

        String jsonBody = objMap.writeValueAsString(productRequestDTO); // transforma o productRequestdto em uma string

        ResultActions result = mockMvc.perform(put("/products/{id}", existingId)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.name").exists());
        result.andExpect(jsonPath("$.description").exists());
        result.andExpect(jsonPath("$.price").exists());
    }

    @Test
    public void updateProductShouldThrowNotFoundExeptionWhenInvalidId() throws Exception {

        String jsonBody = objMap.writeValueAsString(productRequestDTO); // transforma o productRequestdto em uma string Json

        ResultActions result = mockMvc.perform(put("/products/{id}", nonExistingId)
                .content(jsonBody) //define o corpo da requisição, pega o productRequestDTO convertido em Json
                .contentType(MediaType.APPLICATION_JSON) //indica que o contentType vai ser enviado em formato Json
                .accept(MediaType.APPLICATION_JSON)); //indica que o content vai ser  em formato Json

        result.andExpect(status().isNotFound());
    }

    @Test
    public void deleteProductShouldReturnNoContentWhenValidId() throws Exception {

        ResultActions result = mockMvc.perform(delete("/products/{id}", existingId));

        result.andExpect(status().isNoContent());

    }

    @Test
    public void deleteProductShouldThrowNotFoundExceptionWhenInvalidId() throws Exception {

        ResultActions result = mockMvc.perform(delete("/products/{id}", nonExistingId));

        result.andExpect(status().isNotFound());

    }

    @Test
    public void deleteProductShouldThrowBadRequestExceptionWhenDependantId() throws Exception {

        ResultActions result = mockMvc.perform(delete("/products/{id}", dependantId));

        result.andExpect(status().isBadRequest());

    }

    

}
