package korolov.project.domain;

import org.springframework.context.annotation.Scope;

import java.util.Objects;

@Scope("prototype")
public class Shipment {
    private final Order order;
    private String clientAddress;
    private final long trackingNumber;

    public Shipment(Order order, String clientAddress, long trackingNumber) {
        this.order = order;
        this.clientAddress = clientAddress;
        this.trackingNumber = trackingNumber; // key in db
    }

    public Order getOrder() {
        return order;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public long getTrackingNumber() {
        return trackingNumber;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }

    @Override
    public String toString() {
        return "Shipment{" + order + ", clientAddress='" + clientAddress + '\'' + ", trackingNumber='" + trackingNumber + '}';
    }

}

