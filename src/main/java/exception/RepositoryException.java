package exception;

import java.io.Serial;

public class RepositoryException extends Exception {

    @Serial
    private static final long serialVersionUID = -2410524898313246224L;

    public RepositoryException(String message, Exception cause) {
        super(message, cause);
    }
}
