package kg.peaksoft.bilingualb6.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ExceptionResponse {

    private HttpStatus httpStatus;

    private String exceptionClassName;

    private String message;

    public ExceptionResponse(HttpStatus httpStatus, String exceptionClassName, String message) {
        this.httpStatus = httpStatus;
        this.exceptionClassName = exceptionClassName;
        this.message = message;
    }
}
