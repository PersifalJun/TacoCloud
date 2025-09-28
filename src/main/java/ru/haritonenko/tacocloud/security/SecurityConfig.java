package ru.haritonenko.tacocloud.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.haritonenko.tacocloud.repository.UserRepository;
import ru.haritonenko.tacocloud.entity.user.User;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepo) {
        return username -> {
            User user = userRepo.findByUsername(username);
            if (user != null) return user;
            throw new UsernameNotFoundException("User '" + username + "' not found");
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                // Авторизация запросов
                .authorizeHttpRequests(auth -> auth
                        // общедоступные страницы UI
                        .requestMatchers("/", "/login", "/register",
                                "/images/**", "/styles.css", "/webjars/**").permitAll()
                        // H2-консоль
                        .requestMatchers(toH2Console()).permitAll()
                        // OAuth2 редиректы логина (если используешь oauth2Login для UI)
                        .requestMatchers("/oauth2/**", "/login/oauth2/**").permitAll()

                        // --- ПРОВЕРКА СКОУПОВ ДЛЯ API ---
                        .requestMatchers(HttpMethod.POST,   "/data-api/ingredients/**")
                        .hasAuthority("SCOPE_writeIngredients")
                        .requestMatchers(HttpMethod.DELETE, "/data-api/ingredients/**")
                        .hasAuthority("SCOPE_deleteIngredients")

                        // остальное требует аутентификации (GET/PUT/PATCH и т.д.)
                        .anyRequest().authenticated()
                )

                // технастройки для H2-консоли
                .csrf(csrf -> csrf.ignoringRequestMatchers(toH2Console()))
                .headers(h -> h.frameOptions(f -> f.sameOrigin()))

                // форма логина для твоего веб-интерфейса (дизайн/заказы и т.п.)
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/design", true)
                        .permitAll()
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")
                        .defaultSuccessUrl("/design", true)
                )

                // ВКЛЮЧАЕМ ПРОВЕРКУ JWT (это и есть Resource Server)
                .oauth2ResourceServer(oauth -> oauth.jwt(Customizer.withDefaults()))

                .logout(logout -> logout.logoutSuccessUrl("/"))
                .build();
    }




}
