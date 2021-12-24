package korolov.project.business;

import korolov.project.api.exceptions.EntityStateException;
import korolov.project.api.exceptions.HasRelationException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Common superclass for business logic of all entities supporting operations Create, Read, Update, Delete.
 *
 * @param <K> Type of (primary) key.
 * @param <E> Type of entity
 */
public abstract class AbstractCrudService<K, E> {
    /**
     * Reference to data (persistence) layer.
     */
    protected final JpaRepository<E, K> repository;

    protected AbstractCrudService(JpaRepository<E, K> repository) {
        this.repository = repository;
    }

    /**
     * Attempts to store a new entity. Throws exception if an entity with the same key is already stored.
     *
     * @param entity entity to be stored
     * @throws EntityStateException if an entity with the same key is already stored
     */
    public E create(E entity) throws EntityStateException {
        if (exists(entity))
            throw new EntityStateException(entity);
        return repository.save(entity); //delegate call to data layer
    }

    public abstract boolean exists(E entity);

    public Optional<E> readById(K id) {
        return repository.findById(id);
    }

    public List<E> readAll() {
        return repository.findAll();
    }

    /**
     * Attempts to replace an already stored entity.
     *
     * @param entity the new state of the entity to be updated; the instance must contain a key value
     * @throws EntityStateException if the entity cannot be found
     */
    public E update(E entity) throws EntityStateException {
        if (exists(entity))
            return repository.save(entity);
        else
            throw new EntityStateException(entity);
    }

    public void deleteById(K id) throws HasRelationException {
        repository.deleteById(id);
    }

}