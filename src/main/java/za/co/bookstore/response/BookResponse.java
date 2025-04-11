package za.co.bookstore.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BookResponse {
    private String title;
    private String author;
    private String isbn;
}
