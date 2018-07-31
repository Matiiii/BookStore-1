package pl.jstk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import pl.jstk.constants.ModelConstants;
import pl.jstk.constants.ViewNames;
import pl.jstk.service.BookService;
import pl.jstk.to.BookTo;

@Controller
public class BookControler {
    @Autowired
    BookService bookService;

    private static final String INFO_TEXT = "Book was added";
    protected static final String WELCOME = "This is a welcome page";

    @GetMapping("/books")
    public String getAllBooks(Model model){
        model.addAttribute("bookList", bookService.findAllBooks());
        return ViewNames.BOOKS;
    }

    @GetMapping("/books/book")
    public String getBook(Model model, @RequestParam long id){
        BookTo book = bookService.findBookById(id);
        model.addAttribute("book", book );

        return ViewNames.BOOK ;
    }

    @GetMapping("/books/add")
    public String showFormAddBook(@ModelAttribute("newBook") BookTo newBook, Model model){

        return ViewNames.NEW_BOOK ;
    }

    @PostMapping("/greeting")
    public String addBook(@ModelAttribute("newBook") BookTo newBook, Model model){

        BookTo book = bookService.saveBook(newBook);
        model.addAttribute(ModelConstants.MESSAGE, WELCOME );
        model.addAttribute(ModelConstants.INFO, INFO_TEXT );

        return ViewNames.WELCOME ;
    }
}
