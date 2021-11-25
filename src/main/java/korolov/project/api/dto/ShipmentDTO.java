package korolov.project.api.dto;

public class ShipmentDTO {
    public OrderDTO orderDTO;
    public String clientAddress;
    public Long trackingNumber = 0L; // primary key in db

    public ShipmentDTO(OrderDTO orderDTO, String clientAddress, Long trackingNumber) {
        this.orderDTO = orderDTO;
        this.clientAddress = clientAddress;
        this.trackingNumber = trackingNumber;
    }

    public ShipmentDTO(OrderDTO orderDTO, String clientAddress) {
        this.orderDTO = orderDTO;
        this.clientAddress = clientAddress;
    }

    public ShipmentDTO() {
    }

    public OrderDTO getOrder() {
        return orderDTO;
    }

    public void setOrder(OrderDTO orderDTO) {
        this.orderDTO = orderDTO;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }

    public Long getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(Long trackingNumber) {
        this.trackingNumber = trackingNumber;
    }
}
