package korolov.project.api.converter;

import korolov.project.api.dto.OrderDTO;
import korolov.project.domain.Order;

import java.util.Collection;

public class OrderConverter {
    public static Order toModel(OrderDTO orderDTO) {
        return new Order(orderDTO.getOrderId(), orderDTO.getProduct(), orderDTO.getClient());
    }
    public static OrderDTO fromModel(Order order) {
        return new OrderDTO(order.getOrderId(), order.getClient(), order.getProduct());
        // it could not be the best variant to have here Client
        // and Product, will be better to have email and list of id to products
    }
    public static Collection<Order> toModels(Collection<OrderDTO> orderDTOs) {
        return orderDTOs.stream().map(OrderConverter::toModel).toList();
    }
    public static Collection<OrderDTO> fromModels(Collection<Order> orders) {
        return orders.stream().map(OrderConverter::fromModel).toList();
    }
}
