package korolov.project.domain;


import java.util.Objects;

public class Order {

    private final long orderId; //it is database id
    private final Client client;
    private Product product;

    public Order(long orderId, Product product, Client client) {
        this.orderId = orderId;
        this.product = Objects.requireNonNull(product);
        this.client = Objects.requireNonNull(client);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderId == order.orderId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId);
    }
}
