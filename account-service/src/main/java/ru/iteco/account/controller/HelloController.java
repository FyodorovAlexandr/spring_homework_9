package ru.iteco.account.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@Controller
public class HelloController {

    @GetMapping("/hello")
    public String hello(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Model model) {
        model.addAttribute("name", name);
        return "hello";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/hello/{name}")
    public String helloPath(@PathVariable(required = false) String name, Model model) {
        model.addAttribute("name", name.toUpperCase(Locale.ROOT));
        return "hello";
    }

}
