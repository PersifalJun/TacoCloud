package ru.haritonenko.tacocloud.service;

import org.springframework.stereotype.Service;
import ru.haritonenko.tacocloud.repository.OrderRepository;

@Service
public class OrderAdminService {

    private final OrderRepository orderRepository;

    public OrderAdminService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void deleteAllOrders(){
        orderRepository.deleteAll();
    }
}
