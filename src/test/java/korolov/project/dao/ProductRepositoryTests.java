package korolov.project.dao;

import korolov.project.domain.Product;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductRepositoryTests {

    @Autowired
    ProductJpaRepository productJpaRepository;

    @Test
    public void testCreateReadDelete() {

        Product product = new Product("Banana", 10, (long) 1);

        productJpaRepository.save(product);
        List<Product> products = productJpaRepository.findAll();
        assertEquals(1, products.size());

        productJpaRepository.deleteAll();
        Assertions.assertThat(productJpaRepository.findAll()).isEmpty();
    }

    @Test
    public void testFindAllByPriceIsLessThanEqual() {
        Product product = new Product("Banana", 10, (long) 1);
        Product product2 = new Product("Banana", 11, (long) 2);

        productJpaRepository.save(product);
        productJpaRepository.save(product2);
        List<Product> products = productJpaRepository.findAllByPriceIsLessThanEqual(10);
        org.junit.jupiter.api.Assertions.assertEquals(1, products.size());
        org.junit.jupiter.api.Assertions.assertEquals(products.get(0), product);

    }
}
