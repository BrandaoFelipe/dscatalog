package com.brandao.dscatalog.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EmailRequestDTO {

    @NotBlank
    @Email
    private String to;

    @NotBlank
    private String subject;

    @NotBlank
    private String body;

}
