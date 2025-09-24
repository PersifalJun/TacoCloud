package ru.haritonenko.tacocloud.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import ru.haritonenko.tacocloud.entity.TacoOrder;
import ru.haritonenko.tacocloud.entity.user.User;

import java.util.List;


public interface OrderRepository extends CrudRepository<TacoOrder, Long> {


    List<TacoOrder> findByUserOrderByPlacedAtDesc(
            User user, Pageable pageable);
}