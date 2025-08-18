package com.brandao.dscatalog.services;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brandao.dscatalog.dtos.requestDtos.RoleRequestDTO;
import com.brandao.dscatalog.dtos.responseDtos.RoleResponseDTO;
import com.brandao.dscatalog.entities.Roles;
import com.brandao.dscatalog.mappers.RoleMapper;
import com.brandao.dscatalog.repositories.RoleRepository;
import com.brandao.dscatalog.services.exceptions.EmptyRequestException;
import com.brandao.dscatalog.services.exceptions.NotFoundException;

import static com.brandao.dscatalog.mappers.RoleMapper.toResponse;
import static com.brandao.dscatalog.mappers.RoleMapper.applyUpdates;
import static com.brandao.dscatalog.mappers.RoleMapper.toEntity;

@Service
public class RoleService {

    @Autowired
    private RoleRepository repository;

    @Transactional(readOnly = true)
    public Page<RoleResponseDTO> findAllRoles(Pageable pageable) {

        Page<Roles> list = repository.findAll(pageable);

        Page<RoleResponseDTO> dtoPage = list.map(RoleMapper::toResponse);

        return dtoPage;
    }

    @Transactional(readOnly = true)
    public RoleResponseDTO findRoleByid(Long id) {

        Roles entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("id not found"));

        RoleResponseDTO dto = toResponse(entity);

        return dto;
    }

    @Transactional
    public RoleResponseDTO createRole(RoleRequestDTO dto) {

        Objects.requireNonNull(dto, "Request cannot be null");

        Roles entity = toEntity(dto);

        repository.save(entity);

        return toResponse(entity);
    }

    @Transactional
    public RoleResponseDTO updateRole(RoleRequestDTO dto, Long id) {

        Roles entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("id not found"));

        applyUpdates(dto, entity);

        repository.save(entity);

        return toResponse(entity);
    }

    @Transactional
    public void deleteRole(Long id) {

        if (!repository.existsById(id))
            throw new NotFoundException("id not found");

        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Set<Roles> findRolesByNameForInternalUse(List<String>roles) {

        roles = roles.stream().map(name -> "ROLE_" + name.toUpperCase()).toList();

        Set<Roles> entities = repository.searchRolesNames(roles);
        
        if(entities.isEmpty()){
            throw new EmptyRequestException("List is empty");
        }

        return entities;
    }
}
