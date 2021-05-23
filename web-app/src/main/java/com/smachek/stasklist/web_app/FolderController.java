package com.smachek.stasklist.web_app;

import com.smachek.model.Folder;
import com.smachek.service.FolderDtoService;
import com.smachek.service.FolderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class FolderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FolderController.class);


    private final FolderDtoService folderDtoService;
    private final FolderService folderService;

    @Autowired
    public FolderController(FolderDtoService folderDtoService,
                            FolderService folderService) {
        this.folderDtoService = folderDtoService;
        this.folderService = folderService;
    }

    @GetMapping(value = "/folders")
    public final String folders(Model model){
        LOGGER.debug("folders()");
        model.addAttribute("folders", folderDtoService.findAllWithTaskCount());
        return "folders";
    }

    @GetMapping(value = "/folder")
    public final String folderCreatePage(Model model){
        LOGGER.debug("folderCreatePage({})", model);
        model.addAttribute("isNew", true);
        model.addAttribute("folder", new Folder());
        return "folder";
    }

    @PostMapping(value = "/folder")
    public final String folderCreate(Folder folder){
        LOGGER.debug("folderCreate({})", folder);
        folderService.create(folder);
        return "redirect:/folders";
    }

    @GetMapping(value = "/folder/{id}")
    public final String folderEditPage(@PathVariable Integer id, Model model){
        LOGGER.debug("folderEditPage({}, {})", id, model);
        Optional<Folder> optionalFolder = folderService.findById(id);
        if (optionalFolder.isPresent()) {
            model.addAttribute("isNew", false);
            model.addAttribute("folder", optionalFolder.get());
            model.addAttribute("haveTasks", folderService.countTasks(id) > 0);
            return "folder";
        }
        else {
            // TODO folder not found - pass error message as parameter or handle not found error
            return "redirect:/folders";
        }
    }

    @PostMapping(value = "/folder/{id}")
    public final String folderEdit(Folder folder){
        LOGGER.debug("folderEdit({})", folder);
        folderService.update(folder);
        return "redirect:/folders";
    }

    @GetMapping(value = "/folder/{id}/delete")
    public final String folderDeleteById(@PathVariable Integer id){
        LOGGER.debug("folderDeleteById({})", id);
        folderService.delete(id);
        return "redirect:/folders";
    }
}
