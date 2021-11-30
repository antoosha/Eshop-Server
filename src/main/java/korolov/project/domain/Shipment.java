package korolov.project.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "shipment_table")
public class Shipment {
    @Id
    //better to do GenerationType.SEQUENCE
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shipment_id")
    private Long trackingNumber;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private String clientAddress;

    public Shipment(Order order, String clientAddress, Long trackingNumber) {
        this.order = order;
        this.clientAddress = clientAddress;
        if(trackingNumber != null) this.trackingNumber = trackingNumber;
    }

    public Shipment() {

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

    @Override
    public String toString() {
        return "Shipment{" + order + ", clientAddress='" + clientAddress + '\'' + ", trackingNumber='" + trackingNumber + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shipment shipment = (Shipment) o;
        return trackingNumber.equals(shipment.trackingNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trackingNumber);
    }
}

