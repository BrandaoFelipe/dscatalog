package com.brandao.dscatalog.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.brandao.dscatalog.dtos.requestDtos.RoleRequestDTO;
import com.brandao.dscatalog.dtos.responseDtos.RoleResponseDTO;
import com.brandao.dscatalog.services.RoleService;


@RestController
@RequestMapping(path = "/roles")
public class RoleController {

    @Autowired
    private RoleService service;

    @GetMapping
    public ResponseEntity<Page<RoleResponseDTO>> findAll(@PageableDefault(page = 0, size = 12, sort = "authority", direction = Sort.Direction.DESC) Pageable pageable){

        Page<RoleResponseDTO>dtoList = service.findAllRoles(pageable);

        return ResponseEntity.ok(dtoList);

    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<RoleResponseDTO> findById(@PathVariable Long id){

        RoleResponseDTO dto = service.findRoleByid(id);

        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<RoleResponseDTO>newRole(@RequestBody RoleRequestDTO dto){

        RoleResponseDTO response = service.createRole(dto);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();

        return ResponseEntity.created(uri).body(response);

    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<RoleResponseDTO> updateRole(@PathVariable Long id, @RequestBody RoleRequestDTO dto){

        RoleResponseDTO response = service.updateRole(dto, id);

        return ResponseEntity.ok(response);
    }

     @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable Long id) {

        service.deleteRole(id);

        return ResponseEntity.noContent().build();
    }
}
