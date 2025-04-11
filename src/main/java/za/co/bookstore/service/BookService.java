package za.co.bookstore.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import za.co.bookstore.model.Book;
import za.co.bookstore.request.BookRequest;
import za.co.bookstore.response.BookResponse;

import java.util.List;

public interface BookService {
    Book saveBook(BookRequest bookRequest) throws Exception;
    Page<Book> findAllBooks(int page, int size,String sortBy,String direction) throws Exception;
    Book findBooksById(long id) throws Exception;
    Book updateBook(long id,BookRequest bookRequest) throws Exception;
    boolean deleteBook(long id) throws Exception;
    List<Book> searchBooks(String keyword) throws Exception;
}
