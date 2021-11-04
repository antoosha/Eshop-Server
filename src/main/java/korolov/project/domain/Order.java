package korolov.project.domain;


import java.util.Collection;
import java.util.Objects;

public class Order {
    private Long orderId; //it is database id
    private String clientEmail;
    private Collection<Product> products;

    public Order(Long orderId, String clientEmail, Collection<Product> products) {
        this.orderId = orderId;
        this.clientEmail = clientEmail;
        this.products = products;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public Collection<Product> getProducts() {
        return products;
    }

    public void setProducts(Collection<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", clientEmail='" + clientEmail + '\'' +
                ", products={" + products.stream().map(Product::toString) +
                "}}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderId.equals(order.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId);
    }
}
