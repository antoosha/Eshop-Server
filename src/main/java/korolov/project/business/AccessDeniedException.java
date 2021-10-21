package korolov.project.business;

/**
 * An unchecked (runtime) exception indicating illegal operation (authorization denied).
 */
public class AccessDeniedException extends RuntimeException {
    public AccessDeniedException(String s) {
        super(s);
    }
}
