package ru.haritonenko.tacocloud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.haritonenko.tacocloud.repository.OrderRepository;

@Service
@Transactional
public class OrderAdminService {

    private final OrderRepository orderRepository;
    @Autowired
    public OrderAdminService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void deleteAllOrders(){
        orderRepository.deleteAll();
    }
}
