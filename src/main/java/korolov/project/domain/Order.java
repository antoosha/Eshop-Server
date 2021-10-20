package korolov.project.domain;

import org.springframework.context.annotation.Scope;

@Scope("prototype")
public class Order {

    private Product product;
    private final Client client;

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
}
