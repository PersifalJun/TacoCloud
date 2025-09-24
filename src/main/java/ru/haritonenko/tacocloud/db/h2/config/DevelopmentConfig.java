package ru.haritonenko.tacocloud.db.h2.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.haritonenko.tacocloud.repository.IngredientRepository;
import ru.haritonenko.tacocloud.repository.UserRepository;

//Класс нужен для заполнения тестовой бд данным для разработки.

@Profile({"!prod", "!qa"}) //Бин не создается в данных профилях
//@Configuration
public class DevelopmentConfig {
    @Bean
    public CommandLineRunner dataLoader(IngredientRepository repo, UserRepository userRepo, PasswordEncoder encoder) {
        return null;
    }
}