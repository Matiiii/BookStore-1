package pl.jstk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.jstk.constants.ModelConstants;
import pl.jstk.constants.ViewNames;

@Controller
public class LogController {

    private static final String INFO_TEXT = "You are logged";

    @GetMapping(value = "/login")
    public String login() {

        return ViewNames.LOGIN;
    }

    @PostMapping(value = "/login")
    public String login(Model model) {
        model.addAttribute(ModelConstants.INFO,INFO_TEXT);
        return ViewNames.WELCOME;
    }

}
