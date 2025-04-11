package za.co.bookstore.exception;

import org.springframework.http.HttpStatus;

public class ISBNValidationException extends ApiException{
    public ISBNValidationException(String message) {
        super(message, HttpStatus.NOT_ACCEPTABLE, "INVALID ISBN");
    }
}
