package korolov.project.api.controller;

import korolov.project.api.converter.OrderConverter;
import korolov.project.api.dto.OrderDTO;
import korolov.project.api.Exceptions.EntityStateException;
import korolov.project.business.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class OrderController {

    private final OrderService orderService;
    private final OrderConverter orderConverter;

    public OrderController(OrderService orderService, OrderConverter orderConverter) {
        this.orderService = orderService;
        this.orderConverter = orderConverter;
    }

    //CREATE createOrder POST
    @PostMapping("/orders")
    OrderDTO create(@RequestBody OrderDTO orderDTO) {
        try {
            orderDTO = orderConverter.fromModel(orderService.create(orderConverter.toModel(orderDTO)));
        } catch (EntityStateException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order already exists or any product does not exist.");
        }
        return orderDTO;
    }

    //READ showAllOrders GET
    @GetMapping("/orders")
    List<OrderDTO> getAll() {
        return orderConverter.fromModels(orderService.readAll());
    }

    //READ showOrder GET
    @GetMapping("/orders/{id}")
    OrderDTO getOne(@PathVariable long id) {
        return orderConverter.fromModel(orderService.readById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found")
        ));
    }

    //UPDATE editOrder /*change smth in order*/ PUT
    @PutMapping("/orders/{id}")
    OrderDTO update(@PathVariable long id, @RequestBody OrderDTO orderDTO) {
        if (id != orderDTO.getOrderId())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order ids do not match");
        try {
            orderDTO = orderConverter.fromModel(orderService.update(orderConverter.toModel(orderDTO)));
        } catch (EntityStateException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order not found");
        }
        return orderDTO;
    }

    //DELETE deleteOrder /*canceled*/ DELETE
    @DeleteMapping("/orders/{id}")
    void delete(@PathVariable long id) {
        if (orderService.readById(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order to delete not found");
        }
        orderService.deleteById(id);
    }
}
