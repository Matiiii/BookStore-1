package pl.jstk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.jstk.constants.ModelConstants;
import pl.jstk.constants.ViewNames;


@Controller
public class HomeController {

    protected static final String WELCOME = "This is a welcome page";

    @GetMapping(value = "/")
    public String welcome(Model model) {
        model.addAttribute(ModelConstants.MESSAGE, WELCOME);
        //model.addAttribute(ModelConstants.INFO, INFO_TEXT);
        return ViewNames.WELCOME;
    }


}
