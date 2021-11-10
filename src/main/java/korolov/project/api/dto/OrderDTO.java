package korolov.project.api.dto;

import korolov.project.domain.Product;

import java.util.Collection;

public class OrderDTO {
    public Long orderId; //it is database id
    public String clientEmail;
    public Collection<Product> products;

    public OrderDTO(Long orderId, String clientEmail, Collection<Product> products) {
        this.orderId = orderId;
        this.clientEmail = clientEmail;
        this.products = products;
    }

    public OrderDTO() {
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
}
