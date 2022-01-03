package korolov.project.business;

import korolov.project.api.exceptions.EntityStateException;
import korolov.project.api.exceptions.HasRelationException;
import korolov.project.dao.ClientJpaRepository;
import korolov.project.dao.OrderJpaRepository;
import korolov.project.dao.ProductJpaRepository;
import korolov.project.dao.ShipmentJpaRepository;
import korolov.project.domain.Order;
import korolov.project.domain.Shipment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Component
@Transactional
public class OrderService extends AbstractCrudService<Long, Order> {

    private final OrderJpaRepository orderJpaRepository;
    private final ClientJpaRepository clientJpaRepository;
    private final ProductJpaRepository productJpaRepository;
    private final ShipmentJpaRepository shipmentJpaRepository;

    public OrderService(OrderJpaRepository orderJpaRepository, ClientJpaRepository clientJpaRepository,
                        ProductJpaRepository productJpaRepository, ShipmentJpaRepository shipmentJpaRepository) {
        super(orderJpaRepository);
        this.orderJpaRepository = orderJpaRepository;
        this.clientJpaRepository = clientJpaRepository;
        this.productJpaRepository = productJpaRepository;
        this.shipmentJpaRepository = shipmentJpaRepository;
    }

    @Override
    public Order create(Order entity) throws EntityStateException {
        if (!clientJpaRepository.existsById(entity.getClientEmail())
                || entity.getProducts().isEmpty()
                //checks if all given products exists in database
                || entity.getProducts().stream().anyMatch(p -> !productJpaRepository.existsById(p.getProductId()))) {
            throw new EntityStateException();
        }
        return super.create(entity);
    }

    @Override
    public Order update(Order entity) throws EntityStateException {
        if (!exists(entity) || !clientJpaRepository.existsById(entity.getClientEmail())
                || entity.getProducts().isEmpty()) {
            throw new EntityStateException();
        }
        return super.update(entity);
    }

    @Override
    public void deleteById(Long id) throws HasRelationException {
        List<Shipment> shipments = shipmentJpaRepository.findAll();
        for (Shipment shipment : shipments) {
            if (Objects.equals(shipment.getOrder().getOrderId(), id)) {
                throw new HasRelationException();
            }
        }
        super.deleteById(id);
    }

    @Override
    public boolean exists(Order entity) {
        return repository.existsById(entity.getOrderId());
    }

    public List<Order> findAllByClientEmail(String clientEmail) throws EntityStateException {
        if (clientEmail.isBlank() || clientEmail.isEmpty()) throw new EntityStateException();
        return orderJpaRepository.findAllByClientEmail(clientEmail);
    }

    public List<Order> findAllContainsProduct(Long productId) {
        return orderJpaRepository.findAllByProductsContains(productJpaRepository.getById(productId));
    }

}
