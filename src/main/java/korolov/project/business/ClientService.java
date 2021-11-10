package korolov.project.business;

import korolov.project.domain.Client;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Component
@Transactional
public class ClientService extends AbstractCrudService<String, Client> {

    //TODO business logic

    @Override
    public void create(Client entity) throws EntityStateException {
    }

    @Override
    public Optional<Client> readById(String id) {
        return Optional.empty();
    }

    @Override
    public Collection<Client> readAll() {
        return Collections.emptyList();
    }

    @Override
    public void update(Client entity) throws EntityStateException {
    }

    @Override
    public void deleteById(String id) {
    }
}
