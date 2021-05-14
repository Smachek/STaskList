package com.smachek.dao.jdbc;

import com.smachek.dao.FolderDao;
import com.smachek.dao.TaskDao;
import com.smachek.model.Folder;
import com.smachek.model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:test-dao.xml", "classpath*:dao.xml"})
public class TaskDaoJdbcIT {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskDaoJdbcIT.class);

    @Autowired
    private TaskDao taskDao;
    @Autowired
    private FolderDao folderDao;

    @Test
    public void findAllTest() {
        List<Task> tasks = taskDao.findAll();
        Assertions.assertNotNull(tasks);
        Assertions.assertTrue(tasks.size() > 0);
    }

    @Test
    public void findByIdTest(){
        List<Task> tasks = taskDao.findAll();
        Assertions.assertNotNull(tasks);
        Assertions.assertTrue(tasks.size() > 0);

        Integer idTask = tasks.get(0).getIdTask();
        Task actualTask = taskDao.findById(idTask).get();
        Assertions.assertEquals(tasks.get(0), actualTask);
    }

    @Test
    public void findByIdExceptionalTest(){
        Optional<Task> optionalTask = taskDao.findById(999);
        Assertions.assertTrue (optionalTask.isEmpty());
    }

    @Test
    public void createTaskTest() {
        List<Folder> folders = folderDao.findAll();
        Folder folder = folders.get(0);

        List<Task> tasks = taskDao.findAll();
        Assertions.assertNotNull(tasks);
        Assertions.assertTrue(tasks.size() > 0);

        Task newTask = new Task(folder.getIdFolder(), "TASK");
        newTask.setDescription("TASK_DESCRIPTION");
        newTask.setStartDate(new Date());
        newTask.setDueDate(new Date());
        newTask.setPriority(1);
        taskDao.create(newTask);

        List<Task> actualTasks = taskDao.findAll();
        Assertions.assertEquals(tasks.size()+1, actualTasks.size());
    }

    @Test
    public void updateTaskTest() {
        List<Folder> folders = folderDao.findAll();
        Folder folder = folders.get(0);

        List<Task> tasks = taskDao.findAll();
        Assertions.assertNotNull(tasks);
        Assertions.assertTrue(tasks.size() > 0);

        Date today = java.sql.Date.valueOf(LocalDate.now());

        Task task = tasks.get(0);
        task.setIdFolder(folder.getIdFolder());
        task.setNameTask("TASK_update");
        task.setDescription("TASK_DESCRIPTION_update");
        task.setPriority(2);
        task.setStartDate(today);
        task.setDueDate(today);
        task.setDoneMark(Boolean.TRUE);
        task.setDoneDate(today);
        taskDao.update(task);

        Optional<Task> actualTask = taskDao.findById(task.getIdTask());
        Assertions.assertEquals(folder.getIdFolder(), actualTask.get().getIdFolder());
        Assertions.assertEquals("TASK_update", actualTask.get().getNameTask());
        Assertions.assertEquals("TASK_DESCRIPTION_update", actualTask.get().getDescription());
        Assertions.assertEquals((Integer) 2, actualTask.get().getPriority());
        Assertions.assertEquals(today, actualTask.get().getStartDate());
        Assertions.assertEquals(today, actualTask.get().getDueDate());
        Assertions.assertEquals(Boolean.TRUE, actualTask.get().getDoneMark());
        Assertions.assertEquals(today, actualTask.get().getDoneDate());

    }

    @Test
    public void deleteTaskTest() {
        List<Folder> folders = folderDao.findAll();
        Folder folder = folders.get(0);

        Integer deleteIdTask = taskDao.create(new Task(folder.getIdFolder(),"TASK_DELETE"));
        List<Task> tasks = taskDao.findAll();

        int deletedCount = taskDao.delete(deleteIdTask);

        List<Task> actualTasks = taskDao.findAll();
        Assertions.assertEquals(1, deletedCount);
        Assertions.assertEquals(tasks.size()-1, actualTasks.size());
    }

}
