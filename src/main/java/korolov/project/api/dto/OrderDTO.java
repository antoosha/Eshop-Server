package korolov.project.api.dto;

import java.util.ArrayList;
import java.util.List;

public class OrderDTO {
    public Long orderId = 0L; //it is database id
    public String clientEmail;
    public List<ProductDTO> productDTOs = new ArrayList<>();

    public OrderDTO(Long orderId, String clientEmail, List<ProductDTO> productDTOs) {
        this.orderId = orderId;
        this.clientEmail = clientEmail;
        this.productDTOs.addAll(productDTOs);
    }

    public OrderDTO(String clientEmail, List<ProductDTO> productDTOs) {
        this.clientEmail = clientEmail;
        this.productDTOs.addAll(productDTOs);
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

    public List<ProductDTO> getProductDTOs() {
        return productDTOs;
    }

    public void setProductDTOs(List<ProductDTO> productDTOs) {
        this.productDTOs = productDTOs;
    }
}
