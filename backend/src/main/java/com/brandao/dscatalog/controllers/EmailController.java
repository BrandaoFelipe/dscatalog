package com.brandao.dscatalog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brandao.dscatalog.dtos.request.EmailRequestDTO;
import com.brandao.dscatalog.services.MailService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/email")
public class EmailController {

    @Autowired
	private MailService emailService;
	
	@PostMapping
	public ResponseEntity<Void> sendEmail(@Valid @RequestBody EmailRequestDTO obj) {
		emailService.sendEmail(obj);
		return ResponseEntity.noContent().build();
	}

     @Autowired
    private Environment env;

    @GetMapping("/mail-config")
    public String getMailConfig() {
        return String.format(
            "Host: %s, Port: %s, Username: %s, Password: %s",
            env.getProperty("spring.mail.host"),
            env.getProperty("spring.mail.port"),
            env.getProperty("spring.mail.username"),
            env.getProperty("spring.mail.password")
        );
    }
}
