package pl.jstk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.jstk.constants.ViewNames;
import pl.jstk.service.SearchService;
import pl.jstk.to.BookTo;

@Controller()
public class SearchController {

    @Autowired
    SearchService searchService;

    /**
     * This method redirect to a page with form to search books
     *
     * @return view with a form to search
     */
    @GetMapping("/search")
    public String searchBooks() {

        return ViewNames.SEARCH;
    }

    /**
     * This method redirect to view with searched books and a form to search another books
     *
     * @param searchBook - dto with data to search books
     * @param model      - this model accepts list dto of searched books
     * @return view with searched books and form to search
     */
    @PostMapping("/search")
    public String getBooks(@ModelAttribute("searchBook") BookTo searchBook, Model model) {
        model.addAttribute("bookList", searchService.findAllBooks(searchBook));

        return ViewNames.SEARCHED;
    }


}
