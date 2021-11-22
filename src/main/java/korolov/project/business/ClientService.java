package korolov.project.business;

import korolov.project.dao.ClientJpaRepository;
import korolov.project.domain.Client;
import org.springframework.stereotype.Component;


@Component
public class ClientService extends AbstractCrudService<String, Client> {

    //TODO business logic

    public ClientService(ClientJpaRepository clientJpaRepository) {
        super(clientJpaRepository);
    }

    @Override
    public boolean exists(Client entity) {
        return repository.existsById(entity.getEmail());
    }
}
