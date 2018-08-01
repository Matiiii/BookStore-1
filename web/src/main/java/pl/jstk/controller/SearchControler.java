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

@Controller("search")
public class SearchControler {

    @Autowired
    SearchService searchService;


    @GetMapping("/search")
    public String searchBooks(@ModelAttribute("searchBook") BookTo searchBook, Model model){

        return ViewNames.SEARCH ;
    }

    @PostMapping("/search")
    public String getBooks(@ModelAttribute("searchBook") BookTo searchBook, Model model){
        System.out.println("SIZE: " + searchService.findAllBooks(searchBook).size());
        model.addAttribute("bookList", searchService.findAllBooks(searchBook));

        return ViewNames.SEARCHED ;
    }


}
