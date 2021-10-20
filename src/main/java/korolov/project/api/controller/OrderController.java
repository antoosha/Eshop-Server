package korolov.project.api.controller;

import korolov.project.business.OrderService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    //CREATE createOrder POST

    //READ showOrder showAllOrders GET

    //UPDATE editOrder /*change smth in order*/ PUT

    //DELETE deleteOrder /*canceled*/ DELETE

    //TODO implement controller
}
