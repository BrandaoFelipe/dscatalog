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

import com.brandao.dscatalog.dtos.requestDtos.UserRequestDTO;
import com.brandao.dscatalog.dtos.responseDtos.UserResponseDTO;
import com.brandao.dscatalog.services.UserService;


@RestController
@RequestMapping(path = "/users")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping
    public ResponseEntity<Page<UserResponseDTO>> findAll(@PageableDefault(page = 0, size = 12, sort = "firstName", direction = Sort.Direction.DESC) Pageable pageable){

        Page<UserResponseDTO>dtoList = service.findAllUsers(pageable);

        return ResponseEntity.ok(dtoList);

    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id){

        UserResponseDTO dto = service.findUserById(id);

        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO>newUser(@RequestBody UserRequestDTO dto){

        UserResponseDTO response = service.createNewUser(dto);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();

        return ResponseEntity.created(uri).body(response);

    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @RequestBody UserRequestDTO dto){

        UserResponseDTO response = service.updateUser(dto, id);

        return ResponseEntity.ok(response);
    }

     @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable Long id) {

        service.deleteUser(id);

        return ResponseEntity.noContent().build();
    }
}
