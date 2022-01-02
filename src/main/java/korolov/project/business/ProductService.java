package korolov.project.business;

import korolov.project.api.exceptions.EntityStateException;
import korolov.project.api.exceptions.HasRelationException;
import korolov.project.dao.OrderJpaRepository;
import korolov.project.dao.ProductJpaRepository;
import korolov.project.domain.Order;
import korolov.project.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class ProductService extends AbstractCrudService<Long, Product> {

    private ProductJpaRepository productJpaRepository;
    private OrderJpaRepository orderJpaRepository;

    public ProductService(ProductJpaRepository productJpaRepository, OrderJpaRepository orderJpaRepository) {
        super(productJpaRepository);
        this.productJpaRepository = productJpaRepository;
        this.orderJpaRepository = orderJpaRepository;
    }

    /**
     * If parameters in input entity are valid, saves it in database, else throws exception.
     *
     * @param entity entity to be stored
     * @return saved entity
     * @throws EntityStateException
     */
    @Override
    public Product create(Product entity) throws EntityStateException {
        if (entity.getPrice() < 0 || entity.getProductName().isEmpty() || entity.getProductName().isBlank()) {
            throw new EntityStateException();
        }
        return super.create(entity);
    }

    /**
     * If parameters in input entity are valid, updates it in database, else throws exception.
     *
     * @param entity the new state of the entity to be updated; the instance must contain a key value
     * @return updated entity
     * @throws EntityStateException
     */
    @Override
    public Product update(Product entity) throws EntityStateException {
        if (!exists(entity) || entity.getPrice() < 0 || entity.getProductName().isEmpty() || entity.getProductName().isBlank()) {
            throw new EntityStateException();
        }
        return super.update(entity);
    }

    @Override
    public boolean exists(Product entity) {
        return repository.existsById(entity.getProductId());
    }

    @Override
    public void deleteById(Long id) throws HasRelationException {
        List<Order> orders = orderJpaRepository.findAll();
        for (Order order : orders) {
            if (order.getProducts().stream().anyMatch(s -> s.getProductId().equals(id))) {
                throw new HasRelationException();
            }
        }
        super.deleteById(id);
    }

    public List<Product> findAllWithPriceLessThanEqual(double price) throws EntityStateException{
        if(price < 0) throw new EntityStateException();
        return productJpaRepository.findAllByPriceIsLessThanEqual(price);
    }
}
