package korolov.project.api.dto;

import java.util.ArrayList;
import java.util.List;

public class OrderDTO {
    public Long orderId = 0L; //it is database id
    public String clientEmail;
    public List<Long> productIdsDTOs = new ArrayList<>();

    public OrderDTO(Long orderId, String clientEmail, List<Long> productIdsDTOs) {
        this.orderId = orderId;
        this.clientEmail = clientEmail;
        this.productIdsDTOs.addAll(productIdsDTOs);
    }

    public OrderDTO(String clientEmail, List<Long> productIdsDTOs) {
        this.clientEmail = clientEmail;
        this.productIdsDTOs.addAll(productIdsDTOs);
    }

    public OrderDTO(){
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

    public List<Long> getProductIdsDTOs() {
        return productIdsDTOs;
    }

    public void setProductIdsDTOs(List<Long> productIdsDTOs) {
        this.productIdsDTOs = productIdsDTOs;
    }
}
