package korolov.project.api.controller;

import korolov.project.api.converter.OrderConverter;
import korolov.project.api.dto.OrderDTO;
import korolov.project.api.exceptions.EntityStateException;
import korolov.project.api.exceptions.HasRelationException;
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

    @PostMapping("/orders")
    OrderDTO create(@RequestBody OrderDTO orderDTO) {
        try {
            orderDTO = orderConverter.fromModel(orderService.create(orderConverter.toModel(orderDTO)));
        } catch (EntityStateException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Order already exists or any product does not exist.");
        }
        return orderDTO;
    }

    @GetMapping("/orders")
    List<OrderDTO> getAll() {
        return orderConverter.fromModels(orderService.readAll());
    }

    @GetMapping("/orders/{id}")
    OrderDTO getOne(@PathVariable long id) {
        return orderConverter.fromModel(orderService.readById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found")
        ));
    }

    @GetMapping("/orders/client/{email}")
    List<OrderDTO> getByEmail(@PathVariable String email) {
        try {
            return orderConverter.fromModels(orderService.findAllByClientEmail(email));
        } catch (EntityStateException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is not valid");
        }
    }

    @GetMapping("/orders/product/{id}")
    List<OrderDTO> getByProductId(@PathVariable long id) {
        return orderConverter.fromModels(orderService.findAllContainsProduct(id));
    }

    @PutMapping("/orders/{id}")
    OrderDTO update(@PathVariable long id, @RequestBody OrderDTO orderDTO) {
        if (id != orderDTO.getOrderId())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order ids do not match");
        try {
            orderDTO = orderConverter.fromModel(orderService.update(orderConverter.toModel(orderDTO)));
        } catch (EntityStateException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");
        }
        return orderDTO;
    }

    @DeleteMapping("/orders/{id}")
    void delete(@PathVariable long id) {
        if (orderService.readById(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order to delete not found");
        }
        try {
            orderService.deleteById(id);
        } catch (HasRelationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Order has shipment. Could not to delete order");
        }
    }
}
