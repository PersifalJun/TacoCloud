package ru.haritonenko.tacocloud.repository;

import org.springframework.data.repository.CrudRepository;
import ru.haritonenko.tacocloud.entity.TacoOrder;

import java.util.UUID;


public interface OrderRepository extends CrudRepository<TacoOrder, UUID> {

}