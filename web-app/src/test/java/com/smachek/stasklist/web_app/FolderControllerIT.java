package com.smachek.stasklist.web_app;

import com.smachek.model.Folder;
import com.smachek.service.FolderService;
import org.apache.commons.lang3.RandomStringUtils;
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

import static com.smachek.model.constants.FolderConstants.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:app-context-test.xml"})
@Transactional
class FolderControllerIT {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Autowired
    private FolderService folderService;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void shouldReturnFoldersPage() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/folders")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
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

    @Test
    public void shouldReturnCreateFolderPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/folder"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("folder"))
                .andExpect(model().attribute("isNew", is(true)))
                .andExpect(model().attribute("folder", isA(Folder.class)));
    }

    @Test
    public void shouldCreateFolder() throws Exception {
        Integer countBefore = folderService.count();
        mockMvc.perform(
                MockMvcRequestBuilders.post("/folder")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .param("nameFolder", "TEST FOLDER")
                    .param("description", "test folder DESCRIPTION")
        ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/folders"))
                .andExpect(redirectedUrl("/folders"));
        // verify database size
        Integer countAfter = folderService.count();
        assertEquals(countBefore + 1, countAfter);
    }

    @Test
    public void shouldOpenEditFolderPageById() throws Exception {

        Date folderCreateDate = new SimpleDateFormat("yyyy-MM-dd").parse("2020-01-01");
        mockMvc.perform(
                MockMvcRequestBuilders.get("/folder/0")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("folder"))
                .andExpect(model().attribute("isNew",is(false)))
                .andExpect(model().attribute("folder", hasProperty("idFolder", is(0))))
                .andExpect(model().attribute("folder", hasProperty("nameFolder", is("INBOX"))))
                .andExpect(model().attribute("folder", hasProperty("description", is("For new tasks"))))
                .andExpect(model().attribute("folder", hasProperty("createDate", comparesEqualTo(folderCreateDate))));
    }

    @Test
    public void shouldRedirectToFolderPageIfFolderNotFoundById() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/folder/999")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl("folders"));
    }

    @Test
    public void shouldUpdateFolderAfterEdit() throws Exception {
        String testName = RandomStringUtils.randomAlphabetic(FOLDER_NAME_SIZE);
        String testDescription = RandomStringUtils.randomAlphabetic(FOLDER_DESCRIPTION_SIZE);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/folder/1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("idFolder", "1")
                        .param("nameFolder", testName)
                        .param("description", testDescription)
        ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/folders"))
                .andExpect(redirectedUrl("/folders"));

        Optional<Folder> optionalFolder = folderService.findById (1);
        assertTrue(optionalFolder.isPresent());
        assertEquals(testName, optionalFolder.get().getNameFolder());
        assertEquals(testDescription, optionalFolder.get().getDescription());
    }

    @Test
    public void shouldUpdateFolderDescriptionWithSameNameAfterEdit() throws Exception {
        String testDescription = RandomStringUtils.randomAlphabetic(FOLDER_DESCRIPTION_SIZE);
        String testNameFolder = folderService.findById (1).get().getNameFolder();

        mockMvc.perform(
                MockMvcRequestBuilders.post("/folder/1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("idFolder", "1")
                        .param("nameFolder", testNameFolder)
                        .param("description", testDescription)
        ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/folders"))
                .andExpect(redirectedUrl("/folders"));

        Optional<Folder> optionalFolder = folderService.findById (1);
        assertEquals(testDescription, optionalFolder.get().getDescription());
    }
}


