package za.co.bookstore.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import za.co.bookstore.exception.ResourceNotFoundException;
import za.co.bookstore.model.Book;
import za.co.bookstore.repository.BookRepository;
import za.co.bookstore.request.BookRequest;
import za.co.bookstore.service.implementation.BookServiceImplementation;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @Mock
    private BookRepository bookRepository;
    @InjectMocks
    private BookServiceImplementation bookService;
    Book book = new Book(1L,"Poor Dad Rich Dad","May Maile","9785896395355");
    List<Book> bookList = Arrays.asList(
            new Book(2L,"Lift as when rising","Arabang Maile","9785896395357"),
            new Book(2L,"Properties","Ian le Roux","9785896395358"));

    @Test
    void testCreateBook() throws Exception {
         //Arrange
       when(bookRepository.save(any(Book.class))).thenReturn(book);
       //Act
        BookRequest bookRequest =new BookRequest();
        bookRequest.setAuthor(book.getAuthor());
        bookRequest.setTitle(book.getTitle());
        Book savedBook = bookService.saveBook(bookRequest);
        //Assert
        assertNotNull(book);
        assertEquals("Poor Dad Rich Dad",savedBook.getTitle());
        assertEquals("May Maile",savedBook.getAuthor());
        assertEquals("9785896395355",savedBook.getIsbn());
    }
    @Test
    void testGetBookById() throws Exception {
         // Arrange
        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
         // Act
        Book retrieveBook = bookService.findBooksById(book.getId());
         //Assert
        assertNotNull(book);
        assertEquals("Poor Dad Rich Dad",retrieveBook.getTitle());
        assertEquals("9785896395355",retrieveBook.getIsbn());
        assertEquals("May Maile",retrieveBook.getAuthor());
        verify(bookRepository,times(1)).findById(book.getId());
    }
    @Test
    void testGetAllBooks() throws Exception {
        //Arrange
        when(bookRepository.findAll()).thenReturn(bookList);
        //Act
        List<Book> retriveAllBooks = bookService.findAllBooks();
        //Assert
        assertNotNull(bookList);
        assertEquals(2,bookList.size());
        verify(bookRepository,times(1)).findAll();

    }
    @Test
    void testDeleteBook() throws Exception {
        // Arrange
        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        //Act
         bookService.deleteBook(book.getId());
        //Assert
        verify(bookRepository).delete(book);
        verify(bookRepository).findById(book.getId());

    }
    @Test
    void testUpdateBookTest() throws Exception {
        // Arrange
        book.setTitle("New Book");
        book.setAuthor("Thabo Makofane");
        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        when((bookRepository.save(any(Book.class)))).thenAnswer(invocation ->invocation.getArgument(0));
        //Act
        BookRequest bookRequest = new BookRequest();
        bookRequest.setTitle(book.getTitle());
        bookRequest.setAuthor(book.getAuthor());
        Book updatedBook = bookService.updateBook(book.getId(),bookRequest);
        //Assert
        assertNotNull(updatedBook);
        assertEquals("New Book",updatedBook.getTitle());
        assertEquals("Thabo Makofane",updatedBook.getAuthor());
        verify(bookRepository).save(book);

    }
    @Test
    void testBookNotResourceNotFoundException(){
        //arrange
        when(bookRepository.findById(99L)).thenReturn(Optional.empty());
        //act and assert
        assertThrows(ResourceNotFoundException.class,()->bookService.deleteBook(99L));
        verify(bookRepository).findById(99L);
    }
    @Test
    void testSearchBookByTitleAndAuthor() throws Exception {
        //Arrange
        String keyword = bookList.get(1).getTitle();
        when(bookRepository.searchTitleOrAuthor(keyword)).thenReturn(bookList);
        //Act

        List<Book> searchedBooks = bookService.searchBooks(keyword);
        //Assert
        assertNotNull(bookList);
        assertEquals("Properties",searchedBooks.get(1).getTitle());
        verify(bookRepository).searchTitleOrAuthor(keyword);
    }
}
