package korolov.project.domain;


import java.util.Collection;
import java.util.Objects;

public class Order {

    private final long orderId; //it is database id
    private final String clientEmail;
    private Collection<String> products;

    public Order(long orderId, String clientEmail, Collection<String> products) {
        this.orderId = orderId;
        this.clientEmail = clientEmail;
        this.products.addAll(products); // may be a problem
    }

    public long getOrderId() {
        return orderId;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public Collection<String> getProducts() {
        return products;
    }

    public void setProducts(Collection<String> products) {
        this.products.addAll(products); // may be a problem
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", clientEmail='" + clientEmail + '\'' +
                ", products={" + products.stream().toString() + //???
                "}}";
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
