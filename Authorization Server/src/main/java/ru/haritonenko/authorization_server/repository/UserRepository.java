package ru.haritonenko.authorization_server.repository;


import org.springframework.data.repository.CrudRepository;
import ru.haritonenko.authorization_server.entity.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);
}