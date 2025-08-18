package com.brandao.dscatalog.dtos.requestDtos;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String password;

    private List<String>roles = new ArrayList<>();

}
