package korolov.project.domain;

import org.springframework.context.annotation.Scope;

import java.util.Objects;

public class Shipment {
    private final Order order;
    private String clientAddress;
    private final long trackingNumber; // primary key in db

    public Shipment(Order order, String clientAddress, long trackingNumber) {
        this.order = Objects.requireNonNull(order);
        this.clientAddress = clientAddress;
        this.trackingNumber = trackingNumber;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shipment shipment = (Shipment) o;
        return trackingNumber == shipment.trackingNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(trackingNumber);
    }
}

