package korolov.project.business;

import korolov.project.api.exceptions.EntityStateException;
import korolov.project.dao.ClientJpaRepository;
import korolov.project.domain.Client;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@Transactional
public class ClientService extends AbstractCrudService<String, Client> {

    public ClientService(ClientJpaRepository clientJpaRepository) {
        super(clientJpaRepository);
    }

    /**
     * Save entity in database if all parameters in entity are valid else throws exception
     *
     * @param entity entity to be stored
     * @return saved entity
     * @throws EntityStateException
     */
    @Override
    public Client create(Client entity) throws EntityStateException {
        if (!checkIfValid(entity)) {
            throw new EntityStateException();
        }
        entity.setEmail(entity.getEmail().toLowerCase());
        return super.create(entity);
    }


    /**
     * Update entity in database if all parameters in entity are valid else throws exception
     * e-mail could not be changed
     *
     * @param entity the new state of the entity to be updated; the instance must contain a key value
     * @return updated entity
     * @throws EntityStateException
     */
    @Override
    public Client update(Client entity) throws EntityStateException {
        if (!exists(entity) || !checkIfValid(entity)) {
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
     *
     * @param entity to save or update in database
     * @return true if valid, else false
     */
    private boolean checkIfValid(Client entity) {
        return !entity.getEmail().isEmpty() && !entity.getName().isEmpty() && !entity.getSurname().isEmpty()
                && !entity.getEmail().isBlank() && !entity.getName().isBlank() && !entity.getSurname().isBlank()
                && !entity.getName().matches(".*\\d.*") && !entity.getSurname().matches(".*\\d.*")
                && entity.getEmail().contains("@");
    }
}
