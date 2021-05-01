package com.smachek.stasklist.web_app;

import com.smachek.service.FolderDtoService;
import com.smachek.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TaskController {

    private final Logger LOGGER = LoggerFactory.getLogger(TaskController.class);

    private final TaskService taskService;
    private final FolderDtoService folderDtoService;

    public TaskController(TaskService taskService, FolderDtoService folderDtoService) {
        this.taskService = taskService;
        this.folderDtoService = folderDtoService;
    }

    @GetMapping(value = "/tasks")
    public final String tasks(Model model){
        LOGGER.debug("tasks()");
        model.addAttribute("tasks", taskService.findAll());
        model.addAttribute("folders", folderDtoService.findAllWithTaskCount());
        return "tasks";
    }

    @GetMapping(value = "/task")
    public final String task(Model model){
        return "task";
    }

}
