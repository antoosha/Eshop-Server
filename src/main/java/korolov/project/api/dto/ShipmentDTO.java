package korolov.project.api.dto;

import korolov.project.domain.Order;

public class ShipmentDTO {
    public Order order;
    public String clientAddress;
    public Long trackingNumber; // primary key in db

    public ShipmentDTO(Order order, String clientAddress, Long trackingNumber) {
        this.order = order;
        this.clientAddress = clientAddress;
        this.trackingNumber = trackingNumber;
    }

    public ShipmentDTO(Order order, String clientAddress) {
        this.order = order;
        this.clientAddress = clientAddress;
        this.trackingNumber = 0L;
    }

    public ShipmentDTO() {
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }

    public Long getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(Long trackingNumber) {
        this.trackingNumber = trackingNumber;
    }
}
