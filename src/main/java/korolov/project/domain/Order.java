package korolov.project.domain;

import org.springframework.context.annotation.Scope;

@Scope("prototype")
public class Order {

    //private final long orderId; it is database id
    private final Client client;
    private Product product;

    public Order(Product product, Client client) {
        this.product = product;
        this.client = client;
    }

    public Product getProduct() {
        return product;
    }

    public Client getClient() {
        return client;
    }

    @Override
    public String toString() {
        return "Order{" + client + " " + product + "}";
    }

    //TODO Override equals and hashCode
}
