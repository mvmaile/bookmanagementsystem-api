package za.co.bookstore.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BookRequest {
    @NotEmpty(message = "Book Titles must not be empty or blank")
    @NotBlank(message = "Book Titles must not be empty or blank")
    @Size(max = 100,message = "Book Title must not be greater 100")
    private String title;
    @NotEmpty(message = "Book Author must not be empty or blank")
    @NotBlank(message = "Book Author must not be empty or blank")
    @Size(max = 50,message = "Book Author must not be greater than 50")
    private String author;
}
