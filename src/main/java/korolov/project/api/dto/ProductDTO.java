package korolov.project.api.dto;

public class ProductDTO {
    public String productName;
    public double price;
    public long productId; // primary key in db

    public ProductDTO(String productName, double price, long productId) {
        this.productName = productName;
        this.price = price;
        this.productId = productId;
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

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }
}
