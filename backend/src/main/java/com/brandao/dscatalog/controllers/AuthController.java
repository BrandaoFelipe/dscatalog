package com.brandao.dscatalog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.brandao.dscatalog.dtos.request.EmailRequestDTO;
import com.brandao.dscatalog.dtos.request.NewPasswordDTO;
import com.brandao.dscatalog.services.AuthService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/auth")
public class AuthController {

    @Autowired
    private AuthService service;

    @PostMapping(value = "/recover-token")
    public ResponseEntity<Void> createRecoverToken(@Valid @RequestBody EmailRequestDTO dto) {

        service.createRecoverToken(dto);

        return ResponseEntity.noContent().build();

    }

    @PutMapping(value = "/new-password")
    public ResponseEntity<Void> createNewPassword(@Valid  @RequestBody NewPasswordDTO dto) { 

        service.createNewPassword(dto);

        return ResponseEntity.noContent().build();

    }
}
