package korolov.project.api.dto;

public class ProductDTO {
    public String productName;
    public double price;
    public Long productId; // primary key in db

    public ProductDTO(String productName, double price, Long productId) {
        this.productName = productName;
        this.price = price;
        this.productId = productId;
    }

    public ProductDTO(String productName, double price) {
        this.productName = productName;
        this.price = price;
        this.productId = 0L;
    }

    public ProductDTO() {
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
