package com.smachek.service.impl;

import com.smachek.dao.TaskDao;
import com.smachek.model.Task;
import com.smachek.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    private final TaskDao taskDao;

    @Autowired
    public TaskServiceImpl(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    @Override
    public List<Task> findAll() {
        return taskDao.findAll();
    }

    @Override
    public Optional<Task> findById(Integer id) {
        return taskDao.findById(id);
    }

    @Override
    public Integer create(Task task) {
        return taskDao.create(task);
    }

    @Override
    public Integer update(Task task) {
        return taskDao.update(task);
    }

    @Override
    public Integer delete(Integer idTask) {
        return taskDao.delete(idTask);
    }
}
