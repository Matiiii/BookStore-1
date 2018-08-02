package pl.jstk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.jstk.constants.ModelConstants;
import pl.jstk.constants.ViewNames;
import pl.jstk.exception.ResourceNotFoundException;
import pl.jstk.service.BookService;
import pl.jstk.to.BookTo;

@Controller()
public class BookControler {
    @Autowired
    BookService bookService;

    private static final String INFO_ADD_TEXT = "Book was added";
    protected static final String WELCOME = "This is a welcome page";
    protected static final String INFO_NOT_DEL = "You don't have permission";
    protected static final String INFO = "This page contains all informations about books";

    @GetMapping("/books")
    public String getAllBooks(Model model) {
        model.addAttribute("bookList", bookService.findAllBooks());
        return ViewNames.BOOKS;
    }

    @GetMapping("/books/book")
    public String getBook(Model model, @RequestParam long id) {
        BookTo book = bookService.findBookById(id);
        if (book != null) {
            model.addAttribute("book", book);
        } else {
            throw new ResourceNotFoundException();
        }

        return ViewNames.BOOK;
    }

    @GetMapping("/books/add")
    public String showFormAddBook(@ModelAttribute("newBook") BookTo newBook, Model model) {

        return ViewNames.NEW_BOOK;
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/greeting")
    public String addBook(@ModelAttribute("newBook") BookTo newBook, Model model) {

        bookService.saveBook(newBook);
        model.addAttribute(ModelConstants.MESSAGE, WELCOME);
        model.addAttribute(ModelConstants.INFO, INFO_ADD_TEXT);

        return ViewNames.WELCOME;
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/books/delete")
    public String deleteBookForAdmin(@RequestParam long id, RedirectAttributes ra) {

        bookService.deleteBook(id);
        ra.addFlashAttribute("bookList", bookService.findAllBooks());
        ra.addFlashAttribute("message", "Book is deleted");

        return "redirect:/" + ViewNames.BOOKS;
    }


}
