package pl.jstk.service;

import pl.jstk.enumerations.BookStatus;
import pl.jstk.to.BookTo;

import java.util.List;

public interface BookService {

    List<BookTo> findAllBooks();

    List<BookTo> findBooksByTitle(String title);

    List<BookTo> findBooksByAuthor(String author);

    List<BookTo> findBooksByAllFields(String author, String title, BookStatus status);

    BookTo findBookById(long id);

    BookTo saveBook(BookTo book);

    void deleteBook(Long id);
}
