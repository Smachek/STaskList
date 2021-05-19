package com.smachek.dao;

import com.smachek.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskDao {
    List<Task> findAll();
    List<Task> findByFolder(Integer idFolder);
    List<Task> findText(String search);
    Optional<Task> findById(Integer idTask);
    Integer create (Task task);
    Integer update (Task task);
    Integer delete (Integer idTask);

    Integer count();
}
