package pl.jstk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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

    /**
     * This method returns view books and injects all books into it
     * @param model - this model accpets list of dto for all books
     * @return view for all books
     */
    @GetMapping("/books")
    public String getAllBooks(Model model) {
        model.addAttribute("bookList", bookService.findAllBooks());
        return ViewNames.BOOKS;
    }

    /**
     * This method returns view for one a book which id is given by url
     * @param model - this model accepts dto for a book
     * @param id - book's id
     * @return view for one a book
     */
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

    /**
     * This method redirect to page with form to input data of new book
     * @param newBook - dto new book
     * @return view with form to add new book
     */
    @GetMapping("/books/add")
    public String showFormAddBook(@ModelAttribute("newBook") BookTo newBook) {

        return ViewNames.NEW_BOOK;
    }

    /**
     * This method redirect to home page after add new book.
     * This method can be execute when user has admin role.
     * @param newBook - dto with new book
     * @param model - this model accepts welcome message and info that book was added
     * @return view with home page
     */
    @Secured("ROLE_ADMIN")
    @PostMapping("/greeting")
    public String addBook(@ModelAttribute("newBook") BookTo newBook, Model model) {

        bookService.saveBook(newBook);
        model.addAttribute(ModelConstants.MESSAGE, WELCOME);
        model.addAttribute(ModelConstants.INFO, INFO_ADD_TEXT);

        return ViewNames.WELCOME;
    }

    /**
     * This method redirect to page with all books when a book was deleted.
     * This method can be execute when user has admin role
     * @param id - book's id to delete
     * @param ra - redirecct attributes which allow to transfer info that book is deleted to books view.
     * @return - redirect to view of all books
     */
    @Secured("ROLE_ADMIN")
    @PostMapping("/books/delete")
    public String deleteBookForAdmin(@RequestParam long id, RedirectAttributes ra) {

        bookService.deleteBook(id);
        ra.addFlashAttribute("bookList", bookService.findAllBooks());
        ra.addFlashAttribute("message", "Book is deleted");

        return "redirect:/" + ViewNames.BOOKS;
    }


}
