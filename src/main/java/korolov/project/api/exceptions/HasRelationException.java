package korolov.project.api.exceptions;

/**
 * A checked xception, which currently used to indicate that some instance(which we want to delete) has relations
 * with other instances(other words: some instance has our instance inside and it could be correct to delete this instance)
 */
public class HasRelationException extends Exception {
    public HasRelationException() {
    }

    public <E> HasRelationException(E entity) {
        super("Instance has relation in other instance" + entity);
    }
}
