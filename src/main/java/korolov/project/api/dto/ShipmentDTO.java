package korolov.project.api.dto;

public class ShipmentDTO {
    public Long orderIdDTO;
    public String clientAddress;
    public Long trackingNumber = 0L; // primary key in db

    public ShipmentDTO(Long orderIdDTO, String clientAddress, Long trackingNumber) {
        this.orderIdDTO = orderIdDTO;
        this.clientAddress = clientAddress;
        this.trackingNumber = trackingNumber;
    }

    public ShipmentDTO(Long orderIdDTO, String clientAddress) {
        this.orderIdDTO = orderIdDTO;
        this.clientAddress = clientAddress;
    }

    public ShipmentDTO() {
    }

    public Long getOrderIdDTO() {
        return orderIdDTO;
    }

    public void setOrderIdDTO(Long orderIdDTO) {
        this.orderIdDTO = orderIdDTO;
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
