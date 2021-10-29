package korolov.project.api.dto;

import korolov.project.domain.Product;

import java.util.Collection;

public class OrderDTO {
    public long orderId; //it is database id
    public String clientEmail;
    public Collection<Product> products;

    public OrderDTO(long orderId, String clientEmail, Collection<Product> products) {
        this.orderId = orderId;
        this.clientEmail = clientEmail;
        this.products = products;
    }

    public OrderDTO(){}

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
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
