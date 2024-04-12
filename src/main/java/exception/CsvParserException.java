package exception;

import java.io.Serial;

public class CsvParserException extends Exception {

    @Serial
    private static final long serialVersionUID = 7793255389637340001L;

    public CsvParserException(String message, Exception cause) {
        super(message, cause);
    }
}
