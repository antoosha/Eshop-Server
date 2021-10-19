package korolov.project.business;

import java.util.Collection;
import java.util.Optional;

/**
 * Common superclass for business logic of all entities supporting operations Create, Read, Update, Delete.
 *
 * @param <K> Type of (primary) key.
 * @param <E> Type of entity
 */
public abstract class AbstractCrudService<K, E> {

    /**
     * Attempts to store a new entity. Throws exception if an entity with the same key is already stored.
     *
     * @param entity entity to be stored
     * @throws EntityStateException if an entity with the same key is already stored
     */
    public void create(E entity) throws EntityStateException {
       //TODO
    }

    public Optional<E> readById(K id) {
        //TODO
    }

    public Collection<E> readAll() {
        //TODO
    }

    /**
     * Attempts to replace an already stored entity.
     *
     * @param entity the new state of the entity to be updated; the instance must contain a key value
     * @throws EntityStateException if the entity cannot be found
     */
    public void update(E entity) throws EntityStateException {
        //TODO
    }

    public void deleteById(K id) {
        //TODO
    }

}