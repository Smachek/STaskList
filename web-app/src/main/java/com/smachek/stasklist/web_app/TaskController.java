package com.smachek.stasklist.web_app;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TaskController {

    @GetMapping(value = "/tasks")
    public final String tasks(Model model){
        return "tasks";
    }

    @GetMapping(value = "/task")
    public final String task(Model model){
        return "task";
    }

}
