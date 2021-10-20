package korolov.project.domain;

import org.springframework.context.annotation.Scope;

@Scope("prototype")
public class Shipment {
    private final Order order;
    private String clientAddress;

    public Shipment(Order order, String clientAddress) {
        this.order = order;
        this.clientAddress = clientAddress;
    }

    @Override
    public String toString() {
        return "Shipment{" + order + ", clientAddress='" + clientAddress + '\'' + '}';
    }
}

