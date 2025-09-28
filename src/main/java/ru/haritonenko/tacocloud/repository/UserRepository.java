package ru.haritonenko.tacocloud.repository;


import org.springframework.data.repository.CrudRepository;
import ru.haritonenko.tacocloud.entity.user.User;


public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}