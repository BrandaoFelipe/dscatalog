package com.brandao.dscatalog.mappers;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.brandao.dscatalog.dtos.requestDtos.UserRequestDTO;
import com.brandao.dscatalog.dtos.responseDtos.UserResponseDTO;
import com.brandao.dscatalog.entities.Roles;
import com.brandao.dscatalog.entities.User;

@Component
public class UserMapper {

    public static User toEntity(UserRequestDTO dto) {

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

    public static void applyUpdates(UserRequestDTO dto, User entity) {

        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
    }

    public static List<String> getRoles(User entity) {

        Set<Roles> roles = entity.getUserRoles();

        List<String> roleNames = roles.stream().map(x -> x.getAuthority()).toList();

        return roleNames;
    }

}
