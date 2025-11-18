package com.brandao.dscatalog.mappers;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.brandao.dscatalog.dtos.request.UserRequestDTO;
import com.brandao.dscatalog.dtos.response.RoleResponseDTO;
import com.brandao.dscatalog.dtos.response.UserResponseDTO;
import com.brandao.dscatalog.entities.User;
import com.brandao.dscatalog.projections.UserData;

@Component
public class UserMapper {

    public static <T extends UserData> User toEntity(T dto) {

        return User.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();
    }

    public static UserResponseDTO toResponse(User entity) {
        return UserResponseDTO.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .email(entity.getEmail())
                .roles(getRoles(entity))
                .build();
    }

    public static void apply(UserRequestDTO dto, User entity) {

        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        entity.getUserRoles().clear();
    }

    public static Set<RoleResponseDTO> getRoles(User entity) {

       return entity.getUserRoles()
       .stream()
       .map(RoleMapper::toResponse)
       .collect(Collectors.toSet());
    }  

}
