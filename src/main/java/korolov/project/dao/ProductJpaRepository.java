package korolov.project.dao;

import korolov.project.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductJpaRepository extends JpaRepository<Product, Long> {
    /**
     * Find products with price less than input price
     *
     * @param price
     * @return
     */
    List<Product> findAllByPriceIsLessThanEqual(double price);
}
