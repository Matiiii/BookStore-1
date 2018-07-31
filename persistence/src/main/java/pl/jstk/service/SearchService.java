package pl.jstk.service;

import pl.jstk.to.BookTo;

import java.util.List;

public interface SearchService {

    List<BookTo> findAllBooks(BookTo bookTo);

}
