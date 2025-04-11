package za.co.bookstore.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

@Setter
@Getter
public class ErrorResponse {
    private final LocalDateTime timestamp;
    private final int status;
    private final String error;
    private final String errorCode;
    private final String message;
    private final String path;
    public ErrorResponse(HttpStatus status, String errorCode, String message, String path) {
        this.timestamp = LocalDateTime.now();
        this.status = status.value();
        this.error = status.getReasonPhrase();
        this.errorCode = errorCode;
        this.message = message;
        this.path = path;
    }
}
