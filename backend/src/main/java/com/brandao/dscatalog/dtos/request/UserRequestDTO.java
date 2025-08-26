package com.brandao.dscatalog.dtos.request;

import java.util.ArrayList;
import java.util.List;

import com.brandao.dscatalog.customValidations.UserInsertValid;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@UserInsertValid //custom validation annotation
public class UserRequestDTO {

    @NotNull(message = "firstName cannot be null")
    @Size(min = 1, max = 100, message = "firstName must have betwenn 3 and 100 caracters")
    private String firstName;

    @NotNull(message = "lastName cannot be null")
    @Size(min = 1, max = 100, message = "lastName must have betwenn 3 and 100 caracters")
    private String lastName;

    @Email
    @NotNull(message = "email cannot be null")
    @Size(min = 1, max = 100, message = "email syntax not valid")
    private String email;
    private String password;

    private List<String>roles = new ArrayList<>();

}
