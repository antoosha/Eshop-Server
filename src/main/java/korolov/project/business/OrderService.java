package korolov.project.business;

import korolov.project.domain.Client;
import korolov.project.domain.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Component
@Transactional
public class OrderService extends AbstractCrudService<Long, Order> {

    //TODO business logic

    @Override
    public void create(Order entity) throws EntityStateException {
    }

    @Override
    public Optional<Order> readById(Long id) {
        return Optional.empty();
    }

    @Override
    public Collection<Order> readAll() {
        return Collections.emptyList();
    }

    @Override
    public void update(Order entity) throws EntityStateException {
    }

    @Override
    public void deleteById(Long id) {
    }
}
