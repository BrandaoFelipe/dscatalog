package com.brandao.dscatalog.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NewPasswordDTO {

    @NotBlank(message = "Required field")
    private String token;

    @NotBlank
    @Size(min = 8, message = "it must have at least 8 caracters")
    private String password;
}
