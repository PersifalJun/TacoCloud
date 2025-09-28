package ru.haritonenko.authorization_server.init;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.haritonenko.authorization_server.entity.User;
import ru.haritonenko.authorization_server.repository.UserRepository;

@Configuration
public class DevelopmentConfig {

    @Bean
    public ApplicationRunner dataLoader(UserRepository repo, PasswordEncoder encoder) {
        return args -> {
            if (repo.findByUsername("habuma").isEmpty()) {
                var u1 = new User();
                u1.setUsername("habuma");
                u1.setPassword(encoder.encode("password"));
                u1.setRole("ROLE_ADMIN");
                repo.save(u1);
            }
            if (repo.findByUsername("tacochef").isEmpty()) {
                var u2 = new User();
                u2.setUsername("tacochef");
                u2.setPassword(encoder.encode("password"));
                u2.setRole("ROLE_ADMIN");
                repo.save(u2);
            }
        };
    }
}