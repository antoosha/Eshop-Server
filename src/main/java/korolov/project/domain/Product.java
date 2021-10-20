package korolov.project.domain;

import org.springframework.context.annotation.Scope;

@Scope("prototype")
public class Product {
    private String productName;
    private double price;
    private final String productCode;


    public Product(String nameOfProduct, double price, String productCode) {
        this.productName = nameOfProduct;
        this.price = price;
        this.productCode = productCode;
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

    public String getProductCode() {
        return productCode;
    }

    @Override
    public String toString() {
        return "Product{" + "nameOfProduct='" + productName + '\'' + ", price=" + price + '}';
    }

    //TODO Override equals and hashCode
}
