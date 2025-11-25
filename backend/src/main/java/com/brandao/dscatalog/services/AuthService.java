package com.brandao.dscatalog.services;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brandao.dscatalog.dtos.request.EmailRequestDTO;
import com.brandao.dscatalog.dtos.request.NewPasswordDTO;
import com.brandao.dscatalog.entities.PasswordRecover;
import com.brandao.dscatalog.entities.User;
import com.brandao.dscatalog.repositories.PasswordRecoverRepository;
import com.brandao.dscatalog.repositories.UserRepository;
import com.brandao.dscatalog.services.exceptions.EmailException;
import com.brandao.dscatalog.services.exceptions.NotFoundException;

@Service
public class AuthService {

    @Value("${email.password-recover.token.minutes}")
    private Long tokenMinutes;

    @Value("${email.password-recover.uri}")
    private String recoverUri;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordRecoverRepository passwordRepository;

    @Autowired
    private MailService mailService;

    @Autowired
    private UserService userService;

    @Transactional
    public void createRecoverToken(EmailRequestDTO dto) {

        User user = userRepository.findByEmail(dto.getTo());

        if (user == null) {
            throw new NotFoundException("Email not found");
        }

        PasswordRecover passwordRecover = new PasswordRecover();
        String token = UUID.randomUUID().toString();

        passwordRecover.setEmail(dto.getTo());
        passwordRecover.setToken(token);
        passwordRecover.setExpiration(Instant.now().plusSeconds(tokenMinutes * 60L));

        passwordRecover = passwordRepository.save(passwordRecover);

        String body = "Access the link to create a new password\n\n"
                + recoverUri + token;

        mailService.sendEmail(passwordRecover.getEmail(), "Password Recover", body);

    }

    @Transactional
    public void createNewPassword(NewPasswordDTO dto) {

        System.out.println("TOKEN RECEBIDO: " + dto.getToken());

        List<PasswordRecover> validToken = passwordRepository.searchValidTokens(dto.getToken(), Instant.now());

        System.out.println("QUANTIDADE DE TOKENS ENCONTRADOS: " + validToken.size());        

        if (validToken.isEmpty()) {
            throw new EmailException("Invalid token");
        }

        String email = validToken.get(0).getEmail();

        User user = userRepository.findByEmail(email);

        if (email == null) {
            throw new NotFoundException("Email not found");
        }

        user.setPassword(dto.getPassword());

        userService.updateUserInternal(user);

    }

}