package za.co.bookstore.service.implementation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import za.co.bookstore.exception.AlreadyExistException;
import za.co.bookstore.exception.ResourceNotFoundException;
import za.co.bookstore.exception.ValidationException;
import za.co.bookstore.model.Book;
import za.co.bookstore.repository.BookRepository;
import za.co.bookstore.request.BookRequest;
import za.co.bookstore.service.BookService;
import za.co.bookstore.util.ISBNGeneration;

import java.util.List;
@Service
@Slf4j
public class BookServiceImplementation implements BookService {
    @Autowired
    private BookRepository bookRepository;
    /**
     * @param bookRequest
     * @return
     * @throws Exception
     */
    @Override
    public Book saveBook(BookRequest bookRequest) throws Exception {
        log.info("Start to save book details title {} and author {}",bookRequest.getTitle(),bookRequest.getAuthor());
        Book saveBook = null;
        String isbn = null;
        try{
            Book book =new Book();
            try {
                isbn = ISBNGeneration.generateISBN();
                log.info("ISBN Book {}", isbn);

                /**
                 * Checking if ISBN is valid
                 */
                if (!ISBNGeneration.isValidISBN(isbn)) {
                    throw new ValidationException("This ISBN is not vaild" + isbn);
                } else {
                    book.setIsbn(isbn);
                    log.info("ISBN Book is Valid");
                }
            } catch (Exception e) {
                log.info("Failed to generate ISBN because {}",e.getMessage());
                throw e;
            }
            /**
             * Check if isbn already exist
             */
            Book checkBookIsbnExist = bookRepository.findByIsbn(isbn);
            if(checkBookIsbnExist != null){
                throw new AlreadyExistException("ISBN " +isbn+" book already exist");
            }

            /**
             * Checking if the author length >50 and title >100
             */
           if(bookRequest.getAuthor().length() >50 && bookRequest.getTitle().length() > 100){
               throw new ValidationException("Saving Book Author and Title must not be greater than 50 and 100 respectively ");
           }
            /**
             * Checking if title and author is empty and blank
             */
           if(bookRequest.getTitle().isEmpty() || bookRequest.getTitle().isBlank()){
               throw new ValidationException("Saving Book Title must not be empty or blank ");
           }
            if(bookRequest.getAuthor().isEmpty() || bookRequest.getAuthor().isBlank()){
                throw new ValidationException("Saving Book Author must not be empty or blank ");
            }
           book.setAuthor(bookRequest.getAuthor());
           book.setTitle(bookRequest.getTitle());

            /**
             * Doing the saving of book entity
             */
            saveBook = bookRepository.save(book);
           log.info("Manage to save a book with author {} and title {}",saveBook.getAuthor(),saveBook.getTitle());
        }catch (Exception ex){
           log.error("Failed to save book because {}",ex.getMessage());
           throw ex;
        }
        return saveBook;
    }

    /**
     * @return
     * @throws Exception
     */
    @Override
    public Page<Book> findAllBooks(int page, int size,String sortBy,String direction) throws Exception {
        Page<Book> listOfBooks = null;
        try{
            Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
            Pageable pageable = PageRequest.of(page, size);
            listOfBooks = bookRepository.findAll(pageable);
            if(listOfBooks.isEmpty()){
                throw new ResourceNotFoundException("The list of book ","is empty","");
            }
            log.info("Retrieve a list of size {} ",listOfBooks.toList().size());
        } catch (Exception e) {
            log.error("Failed to retrieve book list because of {}",e.getMessage());
            throw e;

        }
        return listOfBooks;
    }

    /**
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public Book findBooksById(long id) throws Exception {
        Book book = null;
        try{
            book = bookRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Book", "id", id));
            log.info("Retrieve a book with title {} ",book.getTitle());
        } catch (Exception e) {
            log.error("Failed to retrieve book because of {}",e.getMessage());
            throw e;
        }
        return book;
    }

    /**
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public Book updateBook(long id,BookRequest bookRequest) throws Exception {
        Book updateBook = null;
        try{
            Book book = bookRepository.findById(id).orElseThrow(() ->
                    new ResourceNotFoundException("Book to update with id","Id ",id));
            if(bookRequest.getAuthor().length() >50 && bookRequest.getTitle().length() > 100){
                throw new ValidationException("Update Book Author and Title must not be greater than 50 and 100 respectively ");
            }
            if(bookRequest.getTitle().isEmpty() || bookRequest.getTitle().isBlank()){
                throw new ValidationException("Update Book Title must not be empty or blank ");
            }
            if(bookRequest.getAuthor().isEmpty() || bookRequest.getAuthor().isBlank()){
                throw new ValidationException("Update Book Author must not be empty or blank ");
            }
                book.setTitle(bookRequest.getTitle());
                book.setAuthor(bookRequest.getAuthor());
                updateBook = bookRepository.save(book);
           log.info("Manage to update book with title {} and author {}",updateBook.getTitle(),updateBook.getAuthor());
        } catch (Exception e) {
            log.error("Failed to update book because of {}",e.getMessage());
            throw e;
        }
        return updateBook;
    }

    /**
     * @param id
     * @throws Exception
     */
    @Override
    public boolean deleteBook(long id) throws Exception {
        boolean isBookDeleted = false;
      try{
         Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Delete Book", "id", id));;
         bookRepository.delete(book);
         isBookDeleted = true;
         log.info("Manage to delete book with ISBN {}",book.getIsbn());
      } catch (Exception e) {
          log.error("Failed to delete book because of {}",e.getMessage());
          throw e;
      }
      return isBookDeleted;
    }

    /**
     * @param keyword
     * @return
     * @throws Exception
     */
    @Override
    public List<Book> searchBooks(String keyword) throws Exception {
        List<Book> searchBookList = null;
        try{
            searchBookList = bookRepository.searchTitleOrAuthor(keyword);
            if(searchBookList.isEmpty()){
                throw new ResourceNotFoundException("The list of searched book ","keyword",keyword);
            }
            log.info("Searched items found and size {}",searchBookList.size());
        } catch (Exception e) {
            log.error("Failed to search book because {}",e.getMessage());
            throw new RuntimeException(e);
        }
        return searchBookList;
    }
}
