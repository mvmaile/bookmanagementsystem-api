package za.co.bookstore.controller;
import jakarta.validation.Valid;
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
@PostMapping()
private ResponseEntity<Book> saveBooks(@Valid @RequestBody  BookRequest bookRequest) throws Exception {
    Book book = bookService.saveBook(bookRequest);
    return new ResponseEntity<>(book,HttpStatus.CREATED);
}
@GetMapping("/{id}")
private ResponseEntity<Object> getBook(@PathVariable long id) throws Exception{
    Book book =bookService.findBooksById(id);
    return new ResponseEntity<>(book, HttpStatus.OK);
}
@PutMapping("/{id}")
private ResponseEntity<Object> updateBook(@PathVariable long id,@Valid @RequestBody  BookRequest bookRequest) throws Exception{
    Book book = bookService.updateBook(id,bookRequest);
    return new ResponseEntity<>(book,HttpStatus.OK);
}
@DeleteMapping("/{id}")
private ResponseEntity<Object> deleteBook(@PathVariable long id) throws Exception{
    return new ResponseEntity<>(bookService.deleteBook(id),HttpStatus.OK);
}
@GetMapping()
private ResponseEntity<Object> getAllPaginationBooks( @RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10") int size,
                                            @RequestParam(defaultValue = "author") String sortBy,
                                            @RequestParam(defaultValue = "ASC") String direction) throws Exception{
    Page<Book> bookList = bookService.findAllBooksAndPaginate(page,size,sortBy,direction);
    return new ResponseEntity<>(bookList,HttpStatus.OK);
}
@GetMapping("/all")
private ResponseEntity<Object> getAllBooks() throws Exception {
    List<Book> bookList = bookService.findAllBooks();
    return new ResponseEntity<>(bookList,HttpStatus.OK);
}
@GetMapping("/search")
    private ResponseEntity<Object> searchBook(@RequestParam String keyword) throws Exception{
    List<Book> bookList = bookService.searchBooks(keyword);
    return new ResponseEntity<>(bookList,HttpStatus.OK);
}
}
