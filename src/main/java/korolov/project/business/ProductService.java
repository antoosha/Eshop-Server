package korolov.project.business;

import korolov.project.api.exceptions.EntityStateException;
import korolov.project.dao.ProductJpaRepository;
import korolov.project.domain.Product;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class ProductService extends AbstractCrudService<Long, Product> {

    //TODO cool business logic to not delete but hide the product

    public ProductService(ProductJpaRepository productJpaRepository) {
        super(productJpaRepository);
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
        if(entity.getPrice() < 0 || entity.getProductName().isEmpty() || entity.getProductName().isBlank()){
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
        if(!exists(entity) || entity.getPrice() < 0 || entity.getProductName().isEmpty() || entity.getProductName().isBlank()){
            throw new EntityStateException();
        }
        return super.update(entity);
    }

    @Override
    public boolean exists(Product entity) {
        return repository.existsById(entity.getProductId());
    }
}
