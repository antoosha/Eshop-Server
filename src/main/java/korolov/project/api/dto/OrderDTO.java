package korolov.project.api.dto;

import korolov.project.domain.Client;
import korolov.project.domain.Product;

public class OrderDTO {
    public final long orderId;
    public final Client client;
    public Product product;

    public OrderDTO(long orderId, Client client, Product product) {
        this.orderId = orderId;
        this.client = client;
        this.product = product;
    }

    public long getOrderId() {
        return orderId;
    }

    public Client getClient() {
        return client;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
