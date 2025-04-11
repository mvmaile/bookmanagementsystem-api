package za.co.bookstore;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;

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
import za.co.bookstore.controller.BookController;
import za.co.bookstore.model.Book;
import za.co.bookstore.service.BookService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@TestConfiguration
class TestSecurityConfig {
    @Bean
    @Primary
    public SecurityFilterChain testSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET,"/api/v1/books/**").permitAll()
                );
        return http.build();
    }
}
@WebMvcTest(BookController.class)
@Import(TestSecurityConfig.class)
public class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @TestConfiguration
    static class TestConfig {
        @Bean
        @Primary
        public BookService bookService() {
            return Mockito.mock(BookService.class);
        }
    }
    @Autowired
    private BookService bookService;
    @Test
    void getBook_shouldReturn200() throws Exception {
        Book mockBook = new Book(3L,"Poor Dad and Rich Dad","May Maile","9785896395355");
        when(bookService.findBooksById(mockBook.getId())).thenReturn(mockBook);
        mockMvc.perform(get("/api/v1/books/"+mockBook.getId()))
                .andExpect(status().isOk());

    }
}
