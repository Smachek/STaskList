package com.smachek.stasklist.web_app;

import com.smachek.model.Folder;
import com.smachek.model.Task;
import com.smachek.service.FolderDtoService;
import com.smachek.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

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
    public final String tasks(@RequestParam(required = false) Integer folder,
                              @RequestParam(required = false) String search,
                              Model model){
        LOGGER.debug("tasks({} {} {})", folder, search, model);
        if (folder != null) {
            model.addAttribute("tasks", taskService.findByFolder(folder));
        } else if (search != null) {
            model.addAttribute("tasks", taskService.findText(search));
        }
        else {
            model.addAttribute("tasks", taskService.findAll());
        }
        model.addAttribute("folders", folderDtoService.findAllWithTaskCount());
        Task task = new Task();
        task.setIdFolder(0);
        model.addAttribute("task", task);
        return "tasks";
    }

    @GetMapping(value = "/task")
    public final String taskCreatePage(Model model){
        LOGGER.debug("taskCreatePage ({})", model);
        model.addAttribute("isNew", true);
        model.addAttribute("task", new Task());
        model.addAttribute("folders", folderDtoService.findAllWithTaskCount() );
        return "task";
    }

    @PostMapping(value = "/task")
    public final String taskCreate(Task task){
        LOGGER.debug("taskCreate({})", task);
        taskService.create(task);
        return "redirect:/tasks";
    }

    @GetMapping(value = "/task/{id}")
    public final String taskEditPage(@PathVariable Integer id, Model model){
        LOGGER.debug("taskEditPage ({} {})", id, model);
        Optional<Task> optionalTask = taskService.findById(id);
        if (optionalTask.isPresent()){
            model.addAttribute("isNew", false);
            model.addAttribute("task", optionalTask.get());
            model.addAttribute("folders", folderDtoService.findAllWithTaskCount() );
            return "task";
        }
        else {
            // TODO folder not found - pass error message as parameter or handle not found error
            return "redirect:/tasks";
        }
    }

    @PostMapping(value = "/task/{id}")
    public final String taskEdit(Task task){
        LOGGER.debug("taskEdit({})", task);
        taskService.update(task);
        return "redirect:/tasks";
    }

    @GetMapping(value = "/task/{id}/delete")
    public final String taskDeleteById(@PathVariable Integer id){
        LOGGER.debug("taskDeleteById({})", id);
        taskService.delete(id);
        return "redirect:/tasks";
    }

}
