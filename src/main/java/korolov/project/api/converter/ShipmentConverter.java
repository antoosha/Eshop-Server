package korolov.project.api.converter;

import korolov.project.api.dto.ShipmentDTO;
import korolov.project.domain.Shipment;

import java.util.Collection;

public class ShipmentConverter {
    public static Shipment toModel(ShipmentDTO shipmentDTO) {
        return new Shipment(shipmentDTO.getOrderId(), shipmentDTO.getClientAddress(), shipmentDTO.getTrackingNumber());
    }
    public static ShipmentDTO fromModel(Shipment shipment) {
        return new ShipmentDTO(shipment.getOrderId(), shipment.getClientAddress(), shipment.getTrackingNumber());
    }
    public static Collection<Shipment> toModels(Collection<ShipmentDTO> shipmentDTOs) {
        return shipmentDTOs.stream().map(ShipmentConverter::toModel).toList();
    }
    public static Collection<ShipmentDTO> fromModels(Collection<Shipment> shipments) {
        return shipments.stream().map(ShipmentConverter::fromModel).toList();
    }
}
