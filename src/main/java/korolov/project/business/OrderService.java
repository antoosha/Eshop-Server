package korolov.project.business;

import korolov.project.dao.OrderJpaRepository;
import korolov.project.domain.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class OrderService extends AbstractCrudService<Long, Order> {

    //TODO business logic

    public OrderService(OrderJpaRepository orderJpaRepository) {
        super(orderJpaRepository);
    }

    @Override
    public boolean exists(Order entity) {
        return repository.existsById(entity.getOrderId());
    }
}
