package ru.haritonenko.tacocloud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.haritonenko.tacocloud.entity.TacoOrder;
import ru.haritonenko.tacocloud.entity.user.User;
import ru.haritonenko.tacocloud.props.OrderProps;
import ru.haritonenko.tacocloud.repository.OrderRepository;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class TacoOrderService {
    private final OrderRepository orderRepository;
    private OrderProps props;
    @Autowired
    public TacoOrderService(OrderRepository orderRepository, OrderProps props) {
        this.orderRepository = orderRepository;
        this.props = props;
    }

    public List<TacoOrder> findByUserOrderByPlacedAt(User user){
        Pageable pageable = PageRequest.of(0, props.getPageSize());
        return orderRepository.findByUserOrderByPlacedAtDesc(user, pageable);
    }
    public Optional<TacoOrder> findTacoOrderById(Long id){
        return orderRepository.findById(id);
    }

    public TacoOrder saveOrder(TacoOrder tacoOrder){
        return orderRepository.save(tacoOrder);
    }

    public void deleteOrderById(Long orderId) {
        orderRepository.deleteById(orderId);
    }
}
