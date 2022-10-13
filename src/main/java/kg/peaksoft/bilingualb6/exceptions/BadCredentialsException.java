package kg.peaksoft.bilingualb6.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class BadCredentialsException extends RuntimeException {

    public BadCredentialsException() {
        super();
    }

    public BadCredentialsException(String message) {
        super(message);
    }
}