package za.co.bookstore.controller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.bookstore.model.Book;
import za.co.bookstore.request.BookRequest;
import za.co.bookstore.service.BookService;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("api/v1/books")
@Slf4j
public class BookController {
    @Autowired
    private BookService bookService;
@PostMapping(path = "")
private ResponseEntity<Object> saveBooks(@RequestBody BookRequest bookRequest) throws Exception {
    Book book = bookService.saveBook(bookRequest);
    return new ResponseEntity<>(book, CREATED);
}
@GetMapping(path = "/{id}")
private ResponseEntity<Object> getBook(@PathVariable long id) throws Exception{
    Book book =bookService.findBooksById(id);
    return new ResponseEntity<>(book, HttpStatus.OK);
}
@PutMapping(path = "/{id}")
private ResponseEntity<Object> updateBook(@PathVariable long id,@RequestBody BookRequest bookRequest) throws Exception{
    Book book = bookService.updateBook(id,bookRequest);
    return new ResponseEntity<>(book,HttpStatus.OK);
}
@DeleteMapping(path = "/{id}")
private ResponseEntity<Object> deleteBook(@PathVariable long id) throws Exception{
    return new ResponseEntity<>(bookService.deleteBook(id),HttpStatus.OK);
}
@GetMapping(path = "")
private ResponseEntity<Object> getAllBooks( @RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10") int size,
                                            @RequestParam(defaultValue = "author") String sortBy,
                                            @RequestParam(defaultValue = "ASC") String direction) throws Exception{
    Page<Book> bookList = bookService.findAllBooks(page,size,sortBy,direction);
    return new ResponseEntity<>(bookList,HttpStatus.OK);
}
@GetMapping(path = "/search")
    private ResponseEntity<Object> searchBook(@RequestParam String keyword) throws Exception{
    List<Book> bookList = bookService.searchBooks(keyword);
    return new ResponseEntity<>(bookList,HttpStatus.OK);
}
}
