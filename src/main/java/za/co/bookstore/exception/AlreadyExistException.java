package za.co.bookstore.exception;

import org.springframework.http.HttpStatus;

public class AlreadyExistException extends ApiException{
    public AlreadyExistException(String message) {
        super(message, HttpStatus.CONFLICT, "CONTENT ALREADY EXIST");
    }
}
