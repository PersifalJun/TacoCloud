package ru.haritonenko.tacocloud.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.haritonenko.tacocloud.entity.user.User;
import ru.haritonenko.tacocloud.repository.UserRepository;
import ru.haritonenko.tacocloud.security.RegistrationForm;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User fromFormToUser(RegistrationForm form){
        return userRepository.save(form.toUser(passwordEncoder));
    }
    public User findUserByUsername(String username){
        return userRepository.findByUsername(username);
    }


}
