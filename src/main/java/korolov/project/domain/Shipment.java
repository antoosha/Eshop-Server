package korolov.project.domain;

import org.springframework.context.annotation.Scope;

@Scope("prototype")
public class Shipment {
    private final Order order;
    private String clientAddress;
    private final String trackingNumber;

    public Shipment(Order order, String clientAddress, String trackingNumber) {
        this.order = order;
        this.clientAddress = clientAddress;
        this.trackingNumber = trackingNumber;
    }

    @Override
    public String toString() {
        return "Shipment{" + order + ", clientAddress='" + clientAddress + '\'' + '}';
    }

    public Order getOrder() {
        return order;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }

    //TODO Override equals and hashCode
}

