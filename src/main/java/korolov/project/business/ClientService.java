package korolov.project.business;

import korolov.project.domain.Client;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
public class ClientService extends AbstractCrudService<String, Client>{
    @Override
    public void create(Client entity) throws EntityStateException {

    }

    @Override
    public Optional<Client> readById(String id) {
        return Optional.empty();
    }

    @Override
    public Collection<Client> readAll() {
        return null;
    }

    @Override
    public void update(Client entity) throws EntityStateException {

    }

    @Override
    public void deleteById(String id) {

    }
    //TODO business logic
}
