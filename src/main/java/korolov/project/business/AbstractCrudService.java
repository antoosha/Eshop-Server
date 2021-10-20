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

    public abstract void create(E entity) throws EntityStateException;

    public abstract Optional<E> readById(K id);

    public abstract Collection<E> readAll();

    public abstract void update(E entity) throws EntityStateException;

    public abstract void deleteById(K id);

}