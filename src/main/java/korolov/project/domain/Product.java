package korolov.project.domain;

import java.util.Objects;


public class Product {
    private String productName;
    private double price;
    private final String productId; // primary key in db


    public Product(String nameOfProduct, double price, String productCode) {
        this.productName = nameOfProduct;
        this.price = price;
        this.productId = Objects.requireNonNull(productCode);
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
        return productId;
    }

    @Override
    public String toString() {
        return "Product{" + "nameOfProduct='" + productName + '\'' + ", price=" + price + "' productId='" + productId +'}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return productId.equals(product.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }
}
