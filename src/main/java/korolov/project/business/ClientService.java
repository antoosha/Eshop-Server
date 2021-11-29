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
    public Client create(Client entity) throws EntityStateException {
        if(!checkIfValid(entity)) {
            throw new EntityStateException();
        }
        entity.setEmail(entity.getEmail().toLowerCase());
        return super.create(entity);
    }


    @Override
    public Client update(Client entity) throws EntityStateException {
        if(!exists(entity) || !checkIfValid(entity) ){
            throw new EntityStateException();
        }
        return super.update(entity);
    }

    @Override
    public boolean exists(Client entity) {
        return repository.existsById(entity.getEmail());
    }

    /**
     * Method to check if all parameters in entity is valid.
     * @param entity to save or update in database
     * @return true if valid, else false
     */
    private boolean checkIfValid(Client entity){
        if(entity.getEmail().isEmpty() || entity.getName().isEmpty() || entity.getSurname().isEmpty() ||
           entity.getEmail().isBlank() || entity.getName().isBlank() || entity.getSurname().isBlank() ||
           !entity.getEmail().contains("@") || entity.getName().matches(".*\\d.*") ||
           entity.getSurname().matches(".*\\d.*")) {
            return false;
        }
        return true;
    }
}
