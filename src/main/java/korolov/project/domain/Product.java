package korolov.project.domain;

import java.util.Objects;


public class Product {
    private String productName;
    private double price;
    private long productId; // primary key in db

    public Product(String productName, double price, long productId) {
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

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "Product{" + "nameOfProduct='" + productName + '\'' + ", price=" + price + "' productId='" + productId + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return productId == product.productId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }
}
