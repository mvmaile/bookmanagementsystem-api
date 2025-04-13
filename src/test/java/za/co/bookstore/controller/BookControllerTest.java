package za.co.bookstore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;
import za.co.bookstore.config.ServiceConfiguration;
import za.co.bookstore.controller.BookController;
import za.co.bookstore.exception.ResourceNotFoundException;
import za.co.bookstore.model.Book;
import za.co.bookstore.request.BookRequest;
import za.co.bookstore.service.BookService;
import za.co.bookstore.service.implementation.BookServiceImplementation;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.hasSize;

@WebMvcTest(BookController.class)
@Import({ServiceConfiguration.class})
public class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private BookService bookService;
    Book mockBook = new Book(3L,"Poor Dad and Rich Dad","May Maile","9785896395355");
    List<Book> bookList = Arrays.asList(
            new Book(1L,"Lift as when rising","Arabang Maile","9785896395357"),
            new Book(2L,"Properties","Ian le Roux","9785896395358"));
    Book mockBookInvalidInput = new Book(3L,"","May Maile","9785896395355");
String baseUrl = "/api/v1/books";
    @Test
    void testCreateBook() throws Exception {
        //Arrange
        BookRequest bookRequest = new BookRequest();
        bookRequest.setAuthor(mockBook.getAuthor());
        bookRequest.setTitle(mockBook.getTitle());
        when(bookService.saveBook(any(BookRequest.class))).thenReturn(mockBook);
        //Act and Assert
        mockMvc.perform(post(baseUrl).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockBook)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value(mockBook.getTitle()))
                .andExpect(jsonPath("$.isbn").value(mockBook.getIsbn()))
                .andExpect(jsonPath("$.author").value(mockBook.getAuthor()));

    }
    @Test
    void testUpdateBook() throws Exception {
        //Arrange
        BookRequest bookRequest = new BookRequest();
        bookRequest.setAuthor(mockBook.getAuthor());
        bookRequest.setTitle(mockBook.getTitle());
        mockBook.setTitle("New Book");
        mockBook.setAuthor("Lebo Mashaba");
        when(bookService.updateBook(eq(mockBook.getId()),any(BookRequest.class))).thenReturn(mockBook);
        //Act and Assert
        mockMvc.perform(put(baseUrl+"/{id}",mockBook.getId()).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockBook)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(mockBook.getTitle()))
                .andExpect(jsonPath("$.isbn").value(mockBook.getIsbn()))
                .andExpect(jsonPath("$.author").value(mockBook.getAuthor()));

    }
    @Test
    void testGetAllBooks() throws Exception {
        //Arrange
        when(bookService.findAllBooks()).thenReturn(bookList);
        //Act and Assert
        mockMvc.perform(get(baseUrl+"/all") .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(jsonPath("$[0].title").value(bookList.get(0).getTitle()))
                .andExpect(jsonPath("$[1].title").value(bookList.get(1).getTitle()));
    }
    @Test
    void testGetBook() throws Exception {
        //Arrange
        when(bookService.findBooksById(mockBook.getId())).thenReturn(mockBook);
        //Act and Assert
        assertNotNull(mockBook);
        mockMvc.perform(get(baseUrl+"/{id}",mockBook.getId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(mockBook.getTitle()));


    }
    @Test
    void testSearchBook() throws Exception {
       //Arrange
        String keyworks = "Arabang";
        when(bookService.searchBooks(keyworks)).thenReturn(bookList);
        //Act and Assert
        assertNotNull(bookList);
        mockMvc.perform(get(baseUrl+"/search?keyword="+keyworks).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
@Test
    void testDeleteBook() throws Exception {
        //Arrange
    doNothing().when(bookService).deleteBook(mockBook.getId());
    //Act and Assert
    mockMvc.perform(delete(baseUrl+"/{id}",mockBook.getId())
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());
}
    @Test
    void testBookResourceNotFoundException() throws Exception {
        // Arrange
        long bookId = 99L;
        doThrow(new ResourceNotFoundException("Book not found","Id",bookId))
                .when(bookService).deleteBook(bookId);

        // Act & Assert
        mockMvc.perform(delete(baseUrl+"/{id}", bookId))
                .andExpect(status().isNoContent());
    }
}
