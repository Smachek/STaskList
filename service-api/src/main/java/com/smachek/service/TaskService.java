package com.smachek.service;

import com.smachek.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    List<Task> findAll();
    List<Task> findByFolder(Integer idFolder);
    Optional<Task> findById(Integer idTask);
    Integer create (Task task);
    Integer update (Task task);
    Integer delete (Integer idTask);

    Integer count();


}
