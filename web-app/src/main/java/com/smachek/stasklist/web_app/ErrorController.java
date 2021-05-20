package com.smachek.stasklist.web_app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

    private final Logger LOGGER = LoggerFactory.getLogger(ErrorController.class);

    @GetMapping(value = "/error")
    public final String ErrorPage(){
        LOGGER.debug("ErrorPage ()");
        return "error";
    }
}
