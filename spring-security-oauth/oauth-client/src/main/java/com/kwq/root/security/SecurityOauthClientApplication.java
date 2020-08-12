package com.kwq.root.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableOAuth2Sso
public class SecurityOauthClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(SecurityOauthClientApplication.class, args);
    }
}
