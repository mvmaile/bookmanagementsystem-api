package za.co.bookstore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import za.co.bookstore.model.Book;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
    @Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(b.author) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Book> searchTitleOrAuthor(@Param("keyword") String keyword) throws Exception;
    Page<Book> findAll(Pageable pageable) ;
    Book findByIsbn(String isbn) throws Exception;
}
