package korolov.project.business;

import korolov.project.domain.Client;
import korolov.project.domain.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Component
public class OrderService extends AbstractCrudService<Client, Order>{
    @Override
    public void create(Order entity) throws EntityStateException {}

    @Override
    public Optional<Order> readById(Client id) {
        return Optional.empty();
    }

    @Override
    public Collection<Order> readAll() {
        return new ArrayList<Order>();
    }

    @Override
    public void update(Order entity) throws EntityStateException {}

    @Override
    public void deleteById(Client id) {}
    //TODO business logic
}
