package korolov.project.api.dto;

import korolov.project.domain.Order;

public class ShipmentDTO {
    public final Order order;
    public String clientAddress;
    public final long trackingNumber;

    public ShipmentDTO(Order order, String clientAddress, long trackingNumber) {
        this.order = order;
        this.clientAddress = clientAddress;
        this.trackingNumber = trackingNumber;
    }

    public Order getOrder() {
        return order;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }

    public long getTrackingNumber() {
        return trackingNumber;
    }
}
