package korolov.project.business;

import korolov.project.api.Exceptions.EntityStateException;
import korolov.project.dao.ShipmentJpaRepository;
import korolov.project.domain.Shipment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class ShipmentService extends AbstractCrudService<Long, Shipment> {

    @Autowired
    private OrderService orderService;

    public ShipmentService(ShipmentJpaRepository shipmentJpaRepository) {
        super(shipmentJpaRepository);
    }

    /**
     * Saves shipment in database if this shipment with same order is not in database
     * and all other parameters are valid.
     * Will not save shipment if order does not exist.
     *
     * @param entity entity to be stored
     * @return saved entity
     * @throws EntityStateException
     */
    @Override
    public Shipment create(Shipment entity) throws EntityStateException {
        if(!orderService.exists(entity.getOrder()) || entity.getClientAddress().isEmpty()
            || entity.getClientAddress().isBlank() || checkIfAnyShipmentWithOrder(entity.getOrder().getOrderId()))
        {
            throw new EntityStateException();
        }
        return super.create(entity);
    }

    @Override
    public Shipment update(Shipment entity) throws EntityStateException {
        if(!exists(entity) || !orderService.exists(entity.getOrder()) || entity.getClientAddress().isEmpty()
             || entity.getClientAddress().isBlank())
        {
            throw new EntityStateException();
        }
        return super.update(entity);
    }

    @Override
    public boolean exists(Shipment entity) {
        return repository.existsById(entity.getTrackingNumber());
    }

    /**
     * Checks if database already stores shipment with this order.
     *
     * @param orderId
     * @return true if shipment with current order already exists else return false
     */
    private boolean checkIfAnyShipmentWithOrder(Long orderId){
        List<Shipment> shipments = repository.findAll();
        for (Shipment shipment : shipments) {
            if(shipment.getOrder().getOrderId().equals(orderId)){
                return true;
            }
        }
        return false;
    }
}
