package ru.haritonenko.tacocloud.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.support.SessionStatus;
import ru.haritonenko.tacocloud.entity.TacoOrder;
import ru.haritonenko.tacocloud.entity.user.User;
import ru.haritonenko.tacocloud.props.OrderProps;
import ru.haritonenko.tacocloud.repository.OrderRepository;
import ru.haritonenko.tacocloud.repository.UserRepository;


@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
public class OrderController {
    private int pageSize = 20;

    private final OrderRepository orderRepo;
    private final UserRepository userRepo;
    private OrderProps props;

    @Autowired
    public OrderController(OrderRepository orderRepo, UserRepository userRepo, OrderProps props) {
        this.orderRepo = orderRepo;
        this.userRepo = userRepo;
        this.props = props;
    }

    @GetMapping("/current")
    public String orderForm(@ModelAttribute("tacoOrder") TacoOrder order,
                            @AuthenticationPrincipal User user) {

        if (order.getDeliveryName() == null) {
            order.setDeliveryName(user.getFullname());
            order.setDeliveryStreet(user.getStreet());
            order.setDeliveryCity(user.getCity());
            order.setDeliveryState(user.getState());
            order.setDeliveryZip(user.getZip());
        }
        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid @ModelAttribute("tacoOrder") TacoOrder order,
                               Errors errors,
                               SessionStatus sessionStatus,
                               @AuthenticationPrincipal User authenticatedUser) {
        if (errors.hasErrors()) return "orderForm";


        String username = authenticatedUser.getUsername();
        var dbUser = userRepo.findByUsername(username);

        if (dbUser == null) throw new IllegalStateException("User not found: " + username);

        order.setUser(dbUser);
        orderRepo.save(order);
        sessionStatus.setComplete();
        return "redirect:/";
    }

    @GetMapping
    public String ordersForUser(
            @AuthenticationPrincipal User user, Model model) {
        Pageable pageable = PageRequest.of(0, props.getPageSize());
        model.addAttribute("orders",
                orderRepo.findByUserOrderByPlacedAtDesc(user, pageable));
        return "orderList";
    }

    @PostAuthorize("hasRole('ADMIN') || " +
            "returnObject.user.username == authentication.name")
    public TacoOrder getOrder(Long id) {
        return orderRepo.findById(id).orElse(null);
    }

}