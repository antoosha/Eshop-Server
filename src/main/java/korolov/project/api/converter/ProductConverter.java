package korolov.project.api.converter;

import korolov.project.api.dto.ProductDTO;
import korolov.project.domain.Product;

import java.util.List;

public class ProductConverter {
    public static Product toModel(ProductDTO productDTO) {
        return new Product(productDTO.getProductName(), productDTO.getPrice(), productDTO.getProductId());
    }

    public static ProductDTO fromModel(Product product) {
        return new ProductDTO(product.getProductName(), product.getPrice(), product.getProductId());
    }

    public static List<Product> toModels(List<ProductDTO> productDTOs) {
        return productDTOs.stream().map(ProductConverter::toModel).toList();
    }

    public static List<ProductDTO> fromModels(List<Product> products) {
        return products.stream().map(ProductConverter::fromModel).toList();
    }
}
