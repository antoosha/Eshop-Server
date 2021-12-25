package korolov.project.dao;

import korolov.project.domain.Product;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductRepositoryTests {

    @Autowired
    ProductJpaRepository productJpaRepository;

    @Test
    public void testCreateReadDelete() {

        Product product = new Product("Banana", 10, (long)1);

        productJpaRepository.save(product);
        List<Product> products = productJpaRepository.findAll();
        Assertions.assertThat(products).extracting(Product::getProductId).containsOnly(Long.valueOf(1));

        productJpaRepository.deleteAll();
        Assertions.assertThat(productJpaRepository.findAll()).isEmpty();
    }
}
