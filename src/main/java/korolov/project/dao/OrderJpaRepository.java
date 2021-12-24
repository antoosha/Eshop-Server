package korolov.project.dao;

import korolov.project.domain.Order;
import korolov.project.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface OrderJpaRepository extends JpaRepository<Order, Long> {
    /**
     * Find all orders of client with email @clientEmail
     *
     * @param clientEmail
     * @return list of orders
     */
    List<Order> findAllByClientEmail(String clientEmail);

    /**
     * Find orders which contains this product.
     *
     * @param product
     * @return
     */
    List<Order> findAllByProductsContains(Product product);

}
