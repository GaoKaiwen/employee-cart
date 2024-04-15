package exception;

import java.io.Serial;

public class CsvRepositoryException extends RepositoryException {

    @Serial
    private static final long serialVersionUID = 4357353452798127821L;

    public CsvRepositoryException(String message, Exception cause) {
        super(message, cause);
    }
}
