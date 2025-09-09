package ru.haritonenko.tacocloud.repository;

import org.springframework.data.repository.CrudRepository;
import ru.haritonenko.tacocloud.entity.TacoOrder;



public interface OrderRepository extends CrudRepository<TacoOrder, Long> {

}