package korolov.project.api.converter;

import korolov.project.api.dto.ShipmentDTO;
import korolov.project.domain.Shipment;

import java.util.List;

public class ShipmentConverter {
    public static Shipment toModel(ShipmentDTO shipmentDTO) {
        return new Shipment(OrderConverter.toModel(shipmentDTO.getOrderDTO()),
                shipmentDTO.getClientAddress(), shipmentDTO.getTrackingNumber());
    }

    public static ShipmentDTO fromModel(Shipment shipment) {
        return new ShipmentDTO(OrderConverter.fromModel(shipment.getOrder()),
                shipment.getClientAddress(), shipment.getTrackingNumber());
    }

    public static List<Shipment> toModels(List<ShipmentDTO> shipmentDTOs) {
        return shipmentDTOs.stream().map(ShipmentConverter::toModel).toList();
    }

    public static List<ShipmentDTO> fromModels(List<Shipment> shipments) {
        return shipments.stream().map(ShipmentConverter::fromModel).toList();
    }
}
