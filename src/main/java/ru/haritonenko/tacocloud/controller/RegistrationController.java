package ru.haritonenko.tacocloud.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.haritonenko.tacocloud.repository.UserRepository;
import ru.haritonenko.tacocloud.security.RegistrationForm;
import ru.haritonenko.tacocloud.service.UserService;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;

    }
    @GetMapping
    public String registerForm() {
        return "registration";
    }
    @PostMapping
    public String processRegistration(RegistrationForm form) {
        userService.fromFormToUser(form);
        return "redirect:/login";
    }
}