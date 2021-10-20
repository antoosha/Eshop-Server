package korolov.project.domain;

import org.springframework.context.annotation.Scope;

@Scope("prototype")
public class Product {
    private String productName;
    private double price;


    public Product(String nameOfProduct, double price) {
        this.productName = nameOfProduct;
        this.price = price;
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

    @Override
    public String toString() {
        return "Product{" + "nameOfProduct='" + productName + '\'' + ", price=" + price + '}';
    }
}
