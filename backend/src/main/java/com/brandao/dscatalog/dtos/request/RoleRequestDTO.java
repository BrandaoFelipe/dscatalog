package com.brandao.dscatalog.dtos.request;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor

public class RoleRequestDTO {
    
    @NotNull(message = "name cannot be null")
    @Size(min = 1, max = 100, message = "name must have betwenn 3 and 100 caracters")
    private String authority;    
}
