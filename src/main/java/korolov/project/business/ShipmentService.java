package korolov.project.business;

import korolov.project.domain.Shipment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Component
@Transactional
public class ShipmentService extends AbstractCrudService<Long, Shipment> {

    //TODO business logic

    @Override
    public void create(Shipment entity) throws EntityStateException {
    }

    @Override
    public Optional<Shipment> readById(Long id) {
        return Optional.empty();
    }

    @Override
    public Collection<Shipment> readAll() {
        return Collections.emptyList();
    }

    @Override
    public void update(Shipment entity) throws EntityStateException {
    }

    @Override
    public void deleteById(Long id) {
    }
}
