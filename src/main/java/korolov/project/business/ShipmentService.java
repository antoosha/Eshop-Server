package korolov.project.business;

import korolov.project.dao.ShipmentJpaRepository;
import korolov.project.domain.Shipment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class ShipmentService extends AbstractCrudService<Long, Shipment> {

    //TODO business logic

    public ShipmentService(ShipmentJpaRepository shipmentJpaRepository) {
        super(shipmentJpaRepository);
    }

    @Override
    public boolean exists(Shipment entity) {
        return repository.existsById(entity.getTrackingNumber());
    }
}
