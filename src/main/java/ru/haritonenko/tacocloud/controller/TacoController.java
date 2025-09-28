//package ru.haritonenko.tacocloud.controller;
//
//
//import org.springframework.dao.EmptyResultDataAccessException;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import ru.haritonenko.tacocloud.entity.Taco;
//import ru.haritonenko.tacocloud.entity.TacoOrder;
//import ru.haritonenko.tacocloud.props.OrderProps;
//import ru.haritonenko.tacocloud.repository.OrderRepository;
//import ru.haritonenko.tacocloud.service.TacoOrderService;
//import ru.haritonenko.tacocloud.service.TacoService;
//
//import java.util.Optional;
//
//import static java.util.Objects.nonNull;
//
//@RestController
//@RequestMapping(path="/api/tacos",
//        produces="application/json")
//@CrossOrigin(origins="http://tacocloud:8080")
//public class TacoController {
//    private final OrderRepository orderRepository;
//    private TacoService tacoService;
//    private TacoOrderService orderService;
//    private OrderProps props;
//
//    public TacoController(TacoService tacoService, OrderProps props, OrderRepository orderRepository) {
//        this.tacoService = tacoService;
//        this.props = props;
//        this.orderRepository = orderRepository;
//    }
//    @GetMapping(params="recent")
//    public Iterable<Taco> recentTacos() {
//        return tacoService.findAllFromPage(props);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Taco> getTaco(@PathVariable("id") Long id) {
//        Optional<Taco> taco = tacoService.getTacoById(id);
//
//        if(taco.isPresent()){
//            return new ResponseEntity(taco.get(),HttpStatus.OK);
//
//        }
//        else{
//            return new ResponseEntity(null,HttpStatus.NOT_FOUND);
//        }
//
//    }
//
//    @PostMapping(consumes="application/json")
//    @ResponseStatus(HttpStatus.CREATED)
//    public Taco postTaco(@RequestBody Taco taco) {
//        return tacoService.saveTaco(taco);
//    }
//
//    //Полная замена ресурса (заказа)
//    @PutMapping(path="/{orderId}", consumes="application/json")
//    public TacoOrder putOrder(
//            @PathVariable("orderId") Long orderId,
//            @RequestBody TacoOrder order) {
//        order.setId(orderId);
//        return orderRepository.save(order);
//    }
//    //Частичная замена ресурса (изменение заказа)
//    @PatchMapping(path="/{orderId}", consumes="application/json")
//    public TacoOrder patchOrder(@PathVariable("orderId") Long orderId,
//                                @RequestBody TacoOrder patch) {
//        TacoOrder order = orderService.findTacoOrderById(orderId).orElseThrow();
//        if (nonNull(patch.getDeliveryName())) {
//            order.setDeliveryName(patch.getDeliveryName());
//        }
//        if (nonNull(patch.getDeliveryStreet())) {
//            order.setDeliveryStreet(patch.getDeliveryStreet());
//        }
//        if (nonNull(patch.getDeliveryCity())) {
//            order.setDeliveryCity(patch.getDeliveryCity());
//        }
//        if (nonNull(patch.getDeliveryState())) {
//            order.setDeliveryState(patch.getDeliveryState());
//        }
//        if (nonNull(patch.getDeliveryZip())) {order.setDeliveryZip(patch.getDeliveryZip());
//        }
//        if (nonNull(patch.getCcNumber())) {
//            order.setCcNumber(patch.getCcNumber());
//        }
//        if (nonNull(patch.getCcExpiration())) {
//            order.setCcExpiration(patch.getCcExpiration());
//        }
//        if (nonNull(patch.getCcCVV())) {
//            order.setCcCVV(patch.getCcCVV());
//        }
//        return orderService.saveOrder(order);
//    }
//
//    @DeleteMapping("/{orderId}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void deleteOrder(@PathVariable("orderId") Long orderId) {
//        try {
//            orderService.deleteOrderById(orderId);
//        } catch (EmptyResultDataAccessException e) {}
//    }
//
//
//}