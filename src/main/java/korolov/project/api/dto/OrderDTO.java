package korolov.project.api.dto;

import korolov.project.domain.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrderDTO {
    public Long orderId; //it is database id
    public String clientEmail;
    public List<Product> products = new ArrayList<>();

    public OrderDTO(Long orderId, String clientEmail, List<Product> products) {
        this.orderId = orderId;
        this.clientEmail = clientEmail;
        this.products.addAll(products);
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

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products.addAll(products);
    }
}
