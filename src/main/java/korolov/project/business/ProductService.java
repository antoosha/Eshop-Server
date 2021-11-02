package korolov.project.business;

import korolov.project.domain.Client;
import korolov.project.domain.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Component
public class ProductService extends AbstractCrudService<Long, Product> {

    //TODO business logic

    @Override
    public void create(Product entity) throws EntityStateException {
    }

    @Override
    public Optional<Product> readById(Long id) {
        return Optional.empty();
    }

    @Override
    public Collection<Product> readAll() {
        return Collections.emptyList();
    }

    @Override
    public void update(Product entity) throws EntityStateException {
    }

    @Override
    public void deleteById(Long id) {
    }
}
