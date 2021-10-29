package korolov.project.api.dto;

public class ProductDTO {
    public String productName;
    public double price;
    public String productId;

    public ProductDTO(String productName, double price, String productId) {
        this.productName = productName;
        this.price = price;
        this.productId = productId;
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

    public String getProductId() {
        return productId;
    }
}
