package ru.haritonenko.client_app.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class ClientSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        // открой публичные страницы/статику по необходимости:
                        .requestMatchers("/", "/css/**", "/images/**", "/webjars/**", "/error").permitAll()
                        .anyRequest().authenticated()
                )
                // OAuth2 Login (Authorization Code)
                .oauth2Login(oauth2 -> oauth2
                        // можешь оставить дефолтную страницу логина, тогда строку ниже убери
                        .loginPage("/oauth2/authorization/taco-admin-client")
                )
                // Включаем клиентскую часть (AuthorizedClientRepository и т.п.)
                .oauth2Client(withDefaults())
                .build();
    }
}