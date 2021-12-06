package korolov.project.api.converter;

import korolov.project.api.dto.ShipmentDTO;
import korolov.project.api.Exceptions.EntityStateException;
import korolov.project.business.OrderService;
import korolov.project.domain.Shipment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ShipmentConverter {

    private final OrderService orderService;

    public ShipmentConverter(OrderService orderService) {
        this.orderService = orderService;
    }

    public Shipment toModel(ShipmentDTO shipmentDTO) throws EntityStateException {
        return new Shipment(
                orderService.readById(shipmentDTO.getOrderIdDTO()).orElseThrow(EntityStateException::new),
                shipmentDTO.getClientAddress(),
                shipmentDTO.getTrackingNumber());
    }

    public ShipmentDTO fromModel(Shipment shipment) {
        return new ShipmentDTO(
                shipment.getOrder().getOrderId(),
                shipment.getClientAddress(),
                shipment.getTrackingNumber());
    }

    public List<Shipment> toModels(List<ShipmentDTO> shipmentDTOs) throws EntityStateException {
        List<Shipment> resultList = new ArrayList<>();
        for (ShipmentDTO shipmentDTO : shipmentDTOs) {
            resultList.add(toModel(shipmentDTO));
        }
        return resultList;
    }

    public List<ShipmentDTO> fromModels(List<Shipment> shipments) {
        return shipments.stream().map(this::fromModel).toList();
    }
}
