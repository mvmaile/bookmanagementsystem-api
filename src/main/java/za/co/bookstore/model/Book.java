package za.co.bookstore.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import za.co.bookstore.common.BaseEntity;
import za.co.bookstore.util.ISBNGeneration;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "Book")
@Setter
@Getter
public class Book extends BaseEntity implements Serializable {
    @Column(name = "title", nullable = false, length = 100)
    private String title;
    @Column(name = "author", nullable = false, length = 50)
    private String author;
    @Column(name = "isbn", nullable = false, length = 13)
    private String isbn;
    public Book(){}
    public Book(long id,String title,String author,String isbn){
        this.isbn = isbn;
        this.setId(id);
        this.author =author;
        this.title = title;
    }
    public Book(long id, String title, String author, String isbn, Date createdAt,Date updatedAt){
        this.isbn = isbn;
        this.setId(id);
        this.author =author;
        this.title = title;
        this.setCreatedAt(createdAt);
        this.setUpdatedAt(updatedAt);
    }
}