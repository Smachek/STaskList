package com.smachek.stasklist.web_app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
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

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:app-context-test.xml"})
@Transactional
class FolderControllerIT {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void shouldReturnFoldersPage() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/folders")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("folders"))
                .andExpect(model().attribute("folders", hasItem(
        allOf(
                                hasProperty("idFolder", is(0)),
                                hasProperty("nameFolder", is("INBOX")),
                                hasProperty("description", is("For new tasks")),
                                hasProperty("taskCount", is(1))
                        )
                )))
                .andExpect(model().attribute("folders", hasItem(
                        allOf(
                                hasProperty("idFolder", is(1)),
                                hasProperty("nameFolder", is("ALL")),
                                hasProperty("description", is("All tasks")),
                                hasProperty("taskCount", is(0))
                        )
                )))
                .andExpect(model().attribute("folders", hasItem(
                        allOf(
                                hasProperty("idFolder", is(2)),
                                hasProperty("nameFolder", is("Personal")),
                                hasProperty("description", is("For personal tasks")),
                                hasProperty("taskCount", is(2))
                        )
                )))
                .andExpect(model().attribute("folders", hasItem(
                        allOf(
                                hasProperty("idFolder", is(3)),
                                hasProperty("nameFolder", is("Work")),
                                hasProperty("description", is("For work tasks")),
                                hasProperty("taskCount", is(1))
                        )
                )));
    }
}


