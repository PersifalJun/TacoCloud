package ru.haritonenko.tacocloud.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
import ru.haritonenko.tacocloud.service.TacoOrderService;
import ru.haritonenko.tacocloud.service.UserService;


@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
public class OrderController {


    private final TacoOrderService orderService;
    private final UserService userService;


    @Autowired
    public OrderController(TacoOrderService orderService, UserService userService, OrderProps props) {
        this.orderService =  orderService;
        this.userService = userService;

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
        var dbUser = userService.findUserByUsername(username);

        if (dbUser == null) throw new IllegalStateException("User not found: " + username);

        order.setUser(dbUser);
        orderService.saveOrder(order);
        sessionStatus.setComplete();
        return "redirect:/";
    }

    @GetMapping
    public String ordersForUser(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("orders", orderService.findByUserOrderByPlacedAt(user));
        return "orderList";
    }

    @PostAuthorize("hasRole('ADMIN') || " +
            "returnObject.user.username == authentication.name")

    public TacoOrder getOrder(Long id) {
        return orderService.findTacoOrderById(id).orElseThrow();
    }

}