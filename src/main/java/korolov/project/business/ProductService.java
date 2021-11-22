package korolov.project.business;

import korolov.project.dao.ProductJpaRepository;
import korolov.project.domain.Product;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class ProductService extends AbstractCrudService<Long, Product> {

    //TODO business logic

    public ProductService(ProductJpaRepository productJpaRepository) {
        super(productJpaRepository);
    }

    @Override
    public boolean exists(Product entity) {
        return repository.existsById(entity.getProductId());
    }
}
