package ru.haritonenko.tacocloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.haritonenko.tacocloud.entity.Taco;

public interface TacoRepository extends JpaRepository<Taco,Long> {
}
