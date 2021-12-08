package korolov.project.api.converter;

import korolov.project.api.dto.OrderDTO;
import korolov.project.api.exceptions.EntityStateException;
import korolov.project.business.ProductService;
import korolov.project.domain.Order;
import korolov.project.domain.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderConverter {

    private final ProductService productService;

    public OrderConverter(ProductService productService) {
        this.productService = productService;
    }

    public Order toModel(OrderDTO orderDTO) throws EntityStateException {
        List<Product> listOfProducts = new ArrayList<>();
        for (Long id :orderDTO.getProductIdsDTOs() ) {
            listOfProducts.add(productService.readById(id).orElseThrow(EntityStateException::new));
        }

        return new Order(orderDTO.getOrderId(), orderDTO.getClientEmail(),listOfProducts);
    }

    public OrderDTO fromModel(Order order) {
        return new OrderDTO(
                order.getOrderId(),
                order.getClientEmail(),
                order.getProducts().stream().map(Product::getProductId).toList());
    }

    public List<Order> toModels(List<OrderDTO> orderDTOs) throws EntityStateException {
        List<Order> resultList = new ArrayList<>();
        for (OrderDTO orderDTO : orderDTOs) {
            resultList.add(toModel(orderDTO));
        }

        return resultList;
    }

    public List<OrderDTO> fromModels(List<Order> orders) {
        return orders.stream().map(this::fromModel).toList();
    }
}
