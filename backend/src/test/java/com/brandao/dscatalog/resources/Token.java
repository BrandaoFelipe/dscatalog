package com.brandao.dscatalog.resources;

import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class Token {

    public static void setupAuthentication(String role) {        
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "test@email.com",
                "password",
                List.of(new SimpleGrantedAuthority(role)));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
