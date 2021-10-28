package korolov.project.api.dto;

public class ShipmentDTO {
    public final long orderId;
    public String clientAddress;
    public final long trackingNumber;

    public ShipmentDTO(long orderId, String clientAddress, long trackingNumber) {
        this.orderId = orderId;
        this.clientAddress = clientAddress;
        this.trackingNumber = trackingNumber;
    }

    public long getOrderId() {
        return orderId;
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
