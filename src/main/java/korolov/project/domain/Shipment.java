package korolov.project.domain;

import org.springframework.context.annotation.Scope;

import java.util.Objects;

public class Shipment {
    private final long orderId;
    private String clientAddress;
    private final long trackingNumber; // primary key in db

    public Shipment(long orderId, String clientAddress, long trackingNumber) {
        this.orderId = orderId;
        this.clientAddress = clientAddress;
        this.trackingNumber = trackingNumber;
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

    public long getOrderId() {
        return orderId;
    }

    @Override
    public String toString() {
        return "Shipment{" + orderId + ", clientAddress='" + clientAddress + '\'' + ", trackingNumber='" + trackingNumber + '}';
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

