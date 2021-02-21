package com.smachek.stasklist.web_app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FolderController {

    @GetMapping(value = "/folders")
    public final String folders(){
        return "folders";
    }

    @GetMapping(value = "/folder")
    public final String folder(){
        return "folder";
    }
}
