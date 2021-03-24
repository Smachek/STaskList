package com.smachek.dao;

import com.smachek.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskDao {
    List<Task> findAll();
    Optional<Task> findById(Integer idTask);
    Integer create (Task task);
    Integer update (Task task);
    Integer delete (Integer idTask);
}
