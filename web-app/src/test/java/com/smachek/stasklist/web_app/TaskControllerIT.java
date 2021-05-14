package com.smachek.stasklist.web_app;

import com.smachek.model.Task;
import com.smachek.service.TaskService;
import org.apache.commons.lang3.RandomStringUtils;
import org.exparity.hamcrest.date.DateMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import static com.smachek.model.constants.TaskConstants.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:app-context-test.xml"})
@Transactional
public class TaskControllerIT {
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Autowired
    private TaskService taskService;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void shouldReturnTasksPage() throws Exception {
        Date createDate = new SimpleDateFormat("yyyy-MM-dd").parse("2020-09-01");  ;
        mockMvc.perform(
                MockMvcRequestBuilders.get("/tasks")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("tasks"))
                .andExpect(model().attribute("tasks", hasItem(
                        allOf(
                                hasProperty("idTask", is(1)),
                                hasProperty("idFolder", is(0)),
                                hasProperty("nameTask", is("Do something till tomorrow")),
                                hasProperty("priority", is(0)),
                                hasProperty("description", is("Description do something.")),
                                hasProperty("startDate",
                                        DateMatchers.sameDay(new SimpleDateFormat("yyyy-MM-dd").parse("2020-09-01"))),
                                hasProperty("dueDate",
                                        DateMatchers.sameDay(new SimpleDateFormat("yyyy-MM-dd").parse("2020-09-10"))),
                                hasProperty("createDate", DateMatchers.sameDay(createDate)),
                                hasProperty("doneMark", is(false)),
                                hasProperty("doneDate", nullValue())
                        )
                )))
                .andExpect(model().attribute("tasks", hasItem(
                        allOf(
                                hasProperty("idTask", is(2)),
                                hasProperty("idFolder", is(2)),
                                hasProperty("nameTask", is("Another task")),
                                hasProperty("priority", is(1)),
                                hasProperty("description", is("Another description")),
                                hasProperty("startDate",
                                        DateMatchers.sameDay(new SimpleDateFormat("yyyy-MM-dd").parse("2020-09-02"))),
                                hasProperty("dueDate", nullValue()),
                                hasProperty("createDate", DateMatchers.sameDay(createDate)),
                                hasProperty("doneMark", is(true)),
                                hasProperty("doneDate", DateMatchers.sameDay(new SimpleDateFormat("yyyy-MM-dd").parse("2020-09-29")))
                        )
                )))
                .andExpect(model().attribute("tasks", hasItem(
                        allOf(
                                hasProperty("idTask", is(3)),
                                hasProperty("idFolder", is(2)),
                                hasProperty("nameTask", is("Develop mega soft")),
                                hasProperty("priority", is(1)),
                                hasProperty("description", is("Huge work")),
                                hasProperty("startDate",
                                        DateMatchers.sameDay(new SimpleDateFormat("yyyy-MM-dd").parse("2020-09-03"))),
                                hasProperty("dueDate",
                                        DateMatchers.sameDay(new SimpleDateFormat("yyyy-MM-dd").parse("2021-09-05"))),
                                hasProperty("createDate", DateMatchers.sameDay(createDate)),
                                hasProperty("doneMark", is(false)),
                                hasProperty("doneDate", nullValue())
                        )
                )))
                .andExpect(model().attribute("tasks", hasItem(
                        allOf(
                                hasProperty("idTask", is(4)),
                                hasProperty("idFolder", is(3)),
                                hasProperty("nameTask", is("Hard task")),
                                hasProperty("priority", is(0)),
                                hasProperty("description", is("")),
                                hasProperty("startDate",
                                        DateMatchers.sameDay(new SimpleDateFormat("yyyy-MM-dd").parse("2020-09-01"))),
                                hasProperty("dueDate", nullValue()),
                                hasProperty("createDate", DateMatchers.sameDay(createDate)),
                                hasProperty("doneMark", is(false)),
                                hasProperty("doneDate", nullValue())
                        )
                )));
    }

    @Test
    public void shouldReturnCreateTaskPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/task"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("task"))
                .andExpect(model().attribute("isNew", is(true)))
                .andExpect(model().attribute("task", isA(Task.class)));
    }

    @Test
    public void shouldCreateTask() throws Exception {
        Integer countBefore = taskService.count();
        mockMvc.perform(
                MockMvcRequestBuilders.post("/task")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("idFolder", "0")
                        .param("nameTask", "TEST TASK")
                        .param("description", "test task DESCRIPTION")
                        .param("priority", "1")
                        .param("startDate", "2021/05/01")
                        .param("dueDate", "2021/05/08")
        ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/tasks"))
                .andExpect(redirectedUrl("/tasks"));
        // verify database size
        Integer countAfter = taskService.count();
        assertEquals(countBefore + 1, countAfter);
    }

    @Test
    public void shouldOpenEditTaskPageById() throws Exception {

        Date taskStartDate = new SimpleDateFormat("yyyy-MM-dd").parse("2020-09-01");
        Date taskDueDate = new SimpleDateFormat("yyyy-MM-dd").parse("2020-09-10");
        Date taskCreateDate = new SimpleDateFormat("yyyy-MM-dd").parse("2020-09-01");
        mockMvc.perform(
                MockMvcRequestBuilders.get("/task/1")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("task"))
                .andExpect(model().attribute("isNew",is(false)))
                .andExpect(model().attribute("task", hasProperty("idTask", is(1))))
                .andExpect(model().attribute("task", hasProperty("idFolder", is(0))))
                .andExpect(model().attribute("task", hasProperty("nameTask", is("Do something till tomorrow"))))
                .andExpect(model().attribute("task", hasProperty("priority", is(0))))
                .andExpect(model().attribute("task", hasProperty("description", is("Description do something."))))
                .andExpect(model().attribute("task", hasProperty("startDate", comparesEqualTo(taskStartDate))))
                .andExpect(model().attribute("task", hasProperty("dueDate", comparesEqualTo(taskDueDate))))
                .andExpect(model().attribute("task", hasProperty("createDate", comparesEqualTo(taskCreateDate))))
                .andExpect(model().attribute("task", hasProperty("doneMark", is(false))));
    }

    @Test
    public void shouldRedirectToTaskPageIfTaskNotFoundById() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/task/999")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/tasks"));
    }

    @Test
    public void shouldUpdateTaskAfterEdit() throws Exception {
        String testName = RandomStringUtils.randomAlphabetic(TASK_NAME_SIZE);
        String testDescription = RandomStringUtils.randomAlphabetic(TASK_DESCRIPTION_SIZE);
        Date taskStartDate = new SimpleDateFormat("yyyy/MM/dd").parse("2021/05/01");
        Date taskDueDate = new SimpleDateFormat("yyyy/MM/dd").parse("2021/05/08");
        mockMvc.perform(
                MockMvcRequestBuilders.post("/task/1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("idTask", "1")
                        .param("idFolder", "0")
                        .param("nameTask", testName)
                        .param("description", testDescription)
                        .param("priority", "1")
                        .param("startDate", "2021/05/01")
                        .param("dueDate", "2021/05/08")
        ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/tasks"))
                .andExpect(redirectedUrl("/tasks"));

        Optional<Task> optionalTask = taskService.findById (1);
        assertTrue(optionalTask.isPresent());
        assertEquals(0, optionalTask.get().getIdFolder());
        assertEquals(testName, optionalTask.get().getNameTask());
        assertEquals(testDescription, optionalTask.get().getDescription());
        assertEquals(1, optionalTask.get().getPriority());
        assertEquals(taskStartDate, optionalTask.get().getStartDate());
        assertEquals(taskDueDate, optionalTask.get().getDueDate());
    }

    @Test
    public void shouldDeleteTask() throws Exception {
        Integer countBefore = taskService.count();
        mockMvc.perform(
                MockMvcRequestBuilders.get("/task/2/delete")
        ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/tasks"))
                .andExpect(redirectedUrl("/tasks"));
        // verify database size
        Integer countAfter = taskService.count();
        assertEquals(countBefore - 1, countAfter);
    }

}
