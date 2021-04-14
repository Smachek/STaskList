package com.smachek.stasklist.web_app;

import com.smachek.service.FolderDtoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class FolderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FolderController.class);

    private final FolderDtoService folderDtoService;

    @Autowired
    public FolderController(FolderDtoService folderDtoService) {
        this.folderDtoService = folderDtoService;
    }

    @GetMapping(value = "/folders")
    public final String folders(Model model){
        LOGGER.debug("folders()");
        model.addAttribute("folders", folderDtoService.findAllWithTaskCount());
        return "folders";
    }

    @GetMapping(value = "/folder/{id}")
    public final String folderEdit(@PathVariable Integer id, Model model){
        LOGGER.debug("folders()");
        return "folder";
    }
}
