package pl.jstk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.jstk.constants.ModelConstants;
import pl.jstk.constants.ViewNames;

import java.nio.file.AccessDeniedException;

@Controller
public class LogController {

    private static final String INFO_TEXT = "You are logged";

    @GetMapping(value = "/login")
    public String login() {

        return ViewNames.LOGIN;
    }

    @GetMapping(value = "/loginError")
    public String loginError(RedirectAttributes ra) {
        ra.addFlashAttribute("error", true);
        return "redirect:/" +ViewNames.LOGIN;
    }

    @PostMapping(value = "/login")
    public String login(Model model) {
        model.addAttribute(ModelConstants.INFO,INFO_TEXT);
        return ViewNames.WELCOME;
    }


}
