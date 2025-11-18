package com.brandao.dscatalog.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.brandao.dscatalog.dtos.request.UserCreatedRequestDTO;
import com.brandao.dscatalog.dtos.request.UserRequestDTO;
import com.brandao.dscatalog.dtos.response.UserResponseDTO;
import com.brandao.dscatalog.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping
    public ResponseEntity<Page<UserResponseDTO>> findAll(
            @PageableDefault(page = 0, size = 12, sort = "firstName", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<UserResponseDTO> dtoList = service.findAllUsers(pageable);

        return ResponseEntity.ok(dtoList);

    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_OPERATOR')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<UserResponseDTO> findById(@Valid @PathVariable Long id) {

        UserResponseDTO dto = service.findUserById(id);

        return ResponseEntity.ok(dto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<UserResponseDTO> newUser(@Valid @RequestBody UserRequestDTO dto) {

        UserResponseDTO response = service.createNewUser(dto);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();

        return ResponseEntity.created(uri).body(response);

    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> newUserCreatedByUser(@Valid @RequestBody UserCreatedRequestDTO dto) {

        UserResponseDTO response = service.userCreated(dto);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();

        return ResponseEntity.created(uri).body(response);

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequestDTO dto) {

        UserResponseDTO response = service.updateUser(dto, id);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteUser(@Valid @PathVariable Long id) {

        service.deleteUser(id);

        return ResponseEntity.noContent().build();
    }
}
