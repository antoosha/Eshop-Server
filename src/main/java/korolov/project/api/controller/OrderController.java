package korolov.project.api.controller;

import korolov.project.api.converter.OrderConverter;
import korolov.project.api.dto.OrderDTO;
import korolov.project.business.EntityStateException;
import korolov.project.business.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;

@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    //CREATE createOrder POST
    @PostMapping("/orders")
    OrderDTO create(@RequestBody OrderDTO orderDTO) {
        try {
            orderService.create(OrderConverter.toModel(orderDTO));
        } catch (EntityStateException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order already exists");
        }
        return getOne(orderDTO.getOrderId());
    }

    //READ showAllOrders GET
    @GetMapping("/orders")
    Collection<OrderDTO> getAll() {
        return OrderConverter.fromModels(orderService.readAll());
    }

    //READ showOrder GET
    @GetMapping("/orders/{id}")
    OrderDTO getOne(@PathVariable long id) {
        return OrderConverter.fromModel(orderService.readById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found")
        ));
    }

    //UPDATE editOrder /*change smth in order*/ PUT
    @PutMapping("/orders/{id}")
    OrderDTO update(@PathVariable long id, @RequestBody OrderDTO orderDTO) {
        if (id != orderDTO.getOrderId())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order ids do not match");
        try {
            orderService.update(OrderConverter.toModel(orderDTO));
        } catch (EntityStateException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");
        }
        return getOne(orderDTO.getOrderId());
    }

    //DELETE deleteOrder /*canceled*/ DELETE
    @DeleteMapping("/orders/{id}")
    void delete(@PathVariable long id) {
        if (orderService.readById(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");
        }
        orderService.deleteById(id);
    }
}
