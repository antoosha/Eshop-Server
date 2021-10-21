package korolov.project.business;

import korolov.project.domain.Shipment;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
public class ShipmentService extends AbstractCrudService<String, Shipment>{
    @Override
    public void create(Shipment entity) throws EntityStateException {}

    @Override
    public Optional<Shipment> readById(String id) {
        return Optional.empty();
    }

    @Override
    public Collection<Shipment> readAll() {
        return null;
    }

    @Override
    public void update(Shipment entity) throws EntityStateException {}

    @Override
    public void deleteById(String id) {}
    //TODO business logic
}
