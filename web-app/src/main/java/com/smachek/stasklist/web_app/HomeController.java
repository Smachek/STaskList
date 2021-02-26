package com.smachek.stasklist.web_app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping(value = "/")
    public final String defaultPageRedirect(){
        return "redirect:tasks";
    }
}
