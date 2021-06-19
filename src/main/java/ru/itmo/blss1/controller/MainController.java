package ru.itmo.blss1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class MainController {
    @GetMapping("/")
    public ModelAndView redirectToSwaggerUi(ModelMap model) {
        return new ModelAndView("redirect:/swagger-ui.html", model);
    }
}
