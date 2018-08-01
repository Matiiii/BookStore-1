package pl.jstk.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.jstk.constants.ModelConstants;
import pl.jstk.constants.ViewNames;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.jstk.exception.ResourceNotFoundException;

@Controller
public class HomeController {

    private static final String INFO_TEXT = "Here You shall display information containing information about newly created TO";
    protected static final String WELCOME = "This is a welcome page";

    @GetMapping(value = "/")
    public String welcome(Model model)  {
        model.addAttribute(ModelConstants.MESSAGE, WELCOME);
        model.addAttribute(ModelConstants.INFO, INFO_TEXT);
        return ViewNames.WELCOME;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    //@ExceptionHandler(ResourceNotFoundException.class)
    public String handleNotFoundException(Model model){
        //MessageTo messageTo = new MessageTo("Content wasn't found");
        model.addAttribute("message", "Content wasn't found" );
        return ViewNames.ERROR;
    }
}
