package korolov.project.api.converter;

import korolov.project.api.dto.OrderDTO;
import korolov.project.domain.Order;

import java.util.List;

public class OrderConverter {
    public static Order toModel(OrderDTO orderDTO) {
        return new Order(orderDTO.getOrderId(),
                         orderDTO.getClientEmail(),
                         ProductConverter.toModels(orderDTO.getProductDTOs()));
    }

    public static OrderDTO fromModel(Order order) {
        return new OrderDTO(order.getOrderId(),
                            order.getClientEmail(),
                            ProductConverter.fromModels(order.getProducts()));
    }

    public static List<Order> toModels(List<OrderDTO> orderDTOs) {
        return orderDTOs.stream().map(OrderConverter::toModel).toList();
    }

    public static List<OrderDTO> fromModels(List<Order> orders) {
        return orders.stream().map(OrderConverter::fromModel).toList();
    }
}
