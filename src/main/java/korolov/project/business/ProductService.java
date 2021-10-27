package korolov.project.business;

import korolov.project.domain.Client;
import korolov.project.domain.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Component
public class ProductService extends AbstractCrudService<String, Product>{
    @Override
    public void create(Product entity) throws EntityStateException {}

    @Override
    public Optional<Product> readById(String id) {
        return Optional.empty();
    }

    @Override
    public Collection<Product> readAll() {
        return new ArrayList<Product>();
    }

    @Override
    public void update(Product entity) throws EntityStateException {}

    @Override
    public void deleteById(String id) {}
    //TODO business logic
}
