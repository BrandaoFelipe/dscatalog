package com.brandao.dscatalog.dtos.request;

import com.brandao.dscatalog.customValidations.UserInsertValid;
import com.brandao.dscatalog.projections.UserData;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserCreatedRequestDTO implements UserData{

    @NotNull(message = "required")
    @Size(min = 3, max = 100, message = "firstName must have betwenn 3 and 100 caracters")
    private String firstName;

    @NotNull(message = "required")
    @Size(min = 3, max = 100, message = "lastName must have betwenn 3 and 100 caracters")
    private String lastName;

    @Email
    @NotNull(message = "required")
    @Size(min = 7, max = 300, message = "email syntax not valid")
    private String email;

    @NotBlank(message = "required")
    @Size(min = 8, message = "must have at least 8 caracteres")
    private String password;    

}
