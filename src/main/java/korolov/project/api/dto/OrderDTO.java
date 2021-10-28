package korolov.project.api.dto;

import korolov.project.domain.Client;
import korolov.project.domain.Product;

import java.util.Collection;

public class OrderDTO {
    public final long orderId; //it is database id
    public final String clientEmail;
    public Collection<String> products;

    public OrderDTO(long orderId, String clientEmail, Collection<String> products) {
        this.orderId = orderId;
        this.clientEmail = clientEmail;
        this.products.addAll(products); //may be a problem
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
        this.products.addAll(products); //may be a problem
    }
}
