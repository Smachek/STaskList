package com.smachek.dao.jdbc;

import com.smachek.dao.FolderDao;
import com.smachek.dao.TaskDao;
import com.smachek.model.Folder;
import com.smachek.model.Task;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:test-dao.xml"})
public class TaskDaoJdbcTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskDaoJdbcTest.class);

    @Autowired
    private TaskDao taskDao;
    @Autowired
    private FolderDao folderDao;

    @Test
    public void findAllTest() {
        List<Task> tasks = taskDao.findAll();
        Assert.assertNotNull(tasks);
        Assert.assertTrue(tasks.size() > 0);
    }

    @Test
    public void findByIdTest(){
        List<Task> tasks = taskDao.findAll();
        Assert.assertNotNull(tasks);
        Assert.assertTrue(tasks.size() > 0);

        Integer idTask = tasks.get(0).getIdTask();
        Task actualTask = taskDao.findById(idTask).get();
        Assert.assertEquals(tasks.get(0), actualTask);
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void findByIdExceptionalTest(){
        taskDao.findById(999).get();
    }

    @Test
    public void createTaskTest() {
        List<Folder> folders = folderDao.findAll();
        Folder folder = folders.get(0);

        List<Task> tasks = taskDao.findAll();
        Assert.assertNotNull(tasks);
        Assert.assertTrue(tasks.size() > 0);

        Task newTask = new Task(folder.getIdFolder(), "TASK");
        newTask.setDescription("TASK_DESCRIPTION");
        newTask.setStartDate(new Date());
        newTask.setDueDate(new Date());
        newTask.setPriority(1);
        taskDao.create(newTask);

        List<Task> actualTasks = taskDao.findAll();
        Assert.assertEquals(tasks.size()+1, actualTasks.size());
    }

    @Test
    public void updateTaskTest() {
        List<Folder> folders = folderDao.findAll();
        Folder folder = folders.get(0);

        List<Task> tasks = taskDao.findAll();
        Assert.assertNotNull(tasks);
        Assert.assertTrue(tasks.size() > 0);

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
        Assert.assertEquals(folder.getIdFolder(), actualTask.get().getIdFolder());
        Assert.assertEquals("TASK_update", actualTask.get().getNameTask());
        Assert.assertEquals("TASK_DESCRIPTION_update", actualTask.get().getDescription());
        Assert.assertEquals((Integer) 2, actualTask.get().getPriority());
        Assert.assertEquals(today, actualTask.get().getStartDate());
        Assert.assertEquals(today, actualTask.get().getDueDate());
        Assert.assertEquals(Boolean.TRUE, actualTask.get().getDoneMark());
        Assert.assertEquals(today, actualTask.get().getDoneDate());

    }

    @Test
    public void deleteTaskTest() {
        List<Folder> folders = folderDao.findAll();
        Folder folder = folders.get(0);

        Integer deleteIdTask = taskDao.create(new Task(folder.getIdFolder(),"TASK_DELETE"));
        List<Task> tasks = taskDao.findAll();

        int deletedCount = taskDao.delete(deleteIdTask);

        List<Task> actualTasks = taskDao.findAll();
        Assert.assertEquals(1, deletedCount);
        Assert.assertEquals(tasks.size()-1, actualTasks.size());
    }

}
