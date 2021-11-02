package korolov.project.api.converter;

import korolov.project.api.dto.ProductDTO;
import korolov.project.domain.Product;

import java.util.Collection;

public class ProductConverter {
    public static Product toModel(ProductDTO productDTO) {
        return new Product(productDTO.getProductName(), productDTO.getPrice(), productDTO.getProductId());
    }

    public static ProductDTO fromModel(Product product) {
        return new ProductDTO(product.getProductName(), product.getPrice(), product.getProductId());
    }

    public static Collection<Product> toModels(Collection<ProductDTO> productDTOs) {
        return productDTOs.stream().map(ProductConverter::toModel).toList();
    }

    public static Collection<ProductDTO> fromModels(Collection<Product> products) {
        return products.stream().map(ProductConverter::fromModel).toList();
    }
}
