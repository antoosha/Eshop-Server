package korolov.project.api.converter;

import korolov.project.api.dto.OrderDTO;
import korolov.project.domain.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderConverter {
    public static Order toModel(OrderDTO orderDTO) {
        return new Order(orderDTO.getOrderId(), orderDTO.getClientEmail(), orderDTO.getProducts());
    }

    public static OrderDTO fromModel(Order order) {
        return new OrderDTO(order.getOrderId(), order.getClientEmail(), order.getProducts());
    }

    public static List<Order> toModels(List<OrderDTO> orderDTOs) {
        /*List<Order> orders = new ArrayList<>();
        orderDTOs.forEach(s->orders.add(toModel(s)));
        return orders;*/
        return orderDTOs.stream().map(OrderConverter::toModel).toList();
    }

    public static List<OrderDTO> fromModels(List<Order> orders) {
        /*List<OrderDTO> orderDTOs = new ArrayList<>();
        orders.forEach(s->orderDTOs.add(fromModel(s)));
        return orderDTOs;*/
        return orders.stream().map(OrderConverter::fromModel).toList();
    }
}
