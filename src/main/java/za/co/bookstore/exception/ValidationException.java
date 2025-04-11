package za.co.bookstore.exception;

import org.springframework.http.HttpStatus;

public class ValidationException extends ApiException{
    public ValidationException(String message) {
        super(message, HttpStatus.BAD_REQUEST, "REQUIRED_VALIDATION_FAILED");
    }
}
