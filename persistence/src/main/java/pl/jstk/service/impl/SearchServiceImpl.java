package pl.jstk.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.jstk.service.BookService;
import pl.jstk.service.SearchService;
import pl.jstk.to.BookTo;

import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private BookService bookService;


    @Override
    public List<BookTo> findAllBooks(BookTo bookTo) {

        return bookService.findBooksByAllFields(bookTo.getAuthors(), bookTo.getTitle(), bookTo.getStatus());

    }
}
