package com.brandao.dscatalog.resources;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.security.core.Authentication;

import com.brandao.dscatalog.dtos.request.ProductRequestDTO;
import com.brandao.dscatalog.tests.Factory;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductResourceIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objMap;

    private long existingId;
    private long nonExistingId;
    private long productTotalCount;

    @BeforeEach
    void setUp() throws Exception {

        existingId = 1L;
        nonExistingId = 999L;
        productTotalCount = 22L;

        setupAuthentication();
    }

    private void setupAuthentication() {        
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "test@email.com",
                "password",
                List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    public void findAllShouldReturnSortedPageWhenSortedByName() throws Exception {
        ResultActions result = mockMvc.perform(get("/products?page=0&size=12&sort=name,asc")
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.totalElements").value(productTotalCount));
        result.andExpect(jsonPath("$.content").exists());
        result.andExpect(jsonPath("$.content[0].name").value("Adaga Goblin"));
        result.andExpect(jsonPath("$.content[1].name").value("Amuleto da Estrela da Noite (Arwen)"));
        result.andExpect(jsonPath("$.content[2].name").value("Arco de Legolas"));

    }

    @Test
    public void updateProductShouldUpdateProductWhenValidId() throws Exception {

        ProductRequestDTO productRequestDTO = Factory.createRequestDTO();

        String jsonBody = objMap.writeValueAsString(productRequestDTO);

        String expectedProductName = productRequestDTO.getName();
        String expectedProductDescription = productRequestDTO.getDescription();

        ResultActions result = mockMvc.perform(put("/products/{id}", existingId)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.id").value(existingId));
        result.andExpect(jsonPath("$.name").value(expectedProductName));
        result.andExpect(jsonPath("$.description").value(expectedProductDescription));
        result.andExpect(jsonPath("$.price").exists());
    }

    @Test
    public void updateProductShouldThrowNotFoundExceptionWhenNonExistantId() throws Exception {

        ProductRequestDTO productRequestDTO = Factory.createRequestDTO();
        String jsonBody = objMap.writeValueAsString(productRequestDTO);

        ResultActions result = mockMvc.perform(put("/products/{id}", nonExistingId)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }

}
