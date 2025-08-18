package com.brandao.dscatalog.mappers;

import org.springframework.stereotype.Component;

import com.brandao.dscatalog.dtos.requestDtos.RoleRequestDTO;
import com.brandao.dscatalog.dtos.responseDtos.RoleResponseDTO;
import com.brandao.dscatalog.entities.Roles;

@Component
public class RoleMapper {

    public static Roles toEntity(RoleRequestDTO dto) {

        return Roles.builder()
                .authority(dto.getAuthority())
                .build();
    }

    public static RoleResponseDTO toResponse(Roles entity) {
        return RoleResponseDTO.builder()
                .id(entity.getId())
                .authority(entity.getAuthority())                
                .build();
    }

    public static void applyUpdates(RoleRequestDTO dto, Roles entity) {

        entity.setAuthority(dto.getAuthority());
    }    

}
