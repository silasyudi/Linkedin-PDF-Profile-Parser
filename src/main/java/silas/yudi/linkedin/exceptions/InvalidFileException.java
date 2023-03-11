package silas.yudi.linkedin.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidFileException extends ResponseStatusException {

    public InvalidFileException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

    public InvalidFileException(Throwable cause) {
        super(HttpStatus.BAD_REQUEST, cause.getMessage(), cause);
    }
}
