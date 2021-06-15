package com.smachek.stasklist.web_app;

import com.smachek.dao.FolderDao;
import com.smachek.dao.FolderDtoDao;
import com.smachek.dao.TaskDao;
import com.smachek.dao.jdbc.FolderDaoJdbc;
import com.smachek.dao.jdbc.FolderDtoDaoJdbc;
import com.smachek.dao.jdbc.TaskDaoJdbc;
import com.smachek.service.FolderDtoService;
import com.smachek.service.FolderService;
import com.smachek.service.TaskService;
import com.smachek.service.impl.FolderDtoServiceImpl;
import com.smachek.service.impl.FolderServiceImpl;
import com.smachek.service.impl.TaskServiceImpl;
import com.smachek.stasklist.testdb.TestDbConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = "com.smachek.*")
@Import(TestDbConfiguration.class)
@PropertySource({"classpath:dao.properties","classpath:application.properties"})
public class AppConfig implements WebMvcConfigurer {

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource){
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    public FolderDao folderDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        return new FolderDaoJdbc(namedParameterJdbcTemplate);
    }

    @Bean
    public FolderDtoDao folderDtoDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        return new FolderDtoDaoJdbc(namedParameterJdbcTemplate);
    }

    @Bean
    public TaskDao taskDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        return new TaskDaoJdbc(namedParameterJdbcTemplate);
    }

    @Bean
    public FolderService folderService(FolderDao folderDao) {
        return new FolderServiceImpl(folderDao);
    }

    @Bean
    public FolderDtoService folderDtoService(FolderDtoDao folderDtoDao) {
        return new FolderDtoServiceImpl(folderDtoDao);
    }

    @Bean
    public TaskService taskService(TaskDao taskDao) {
        return new TaskServiceImpl(taskDao);
    }

    @Controller
    static class FaviconController {
        @RequestMapping("favicon.ico")
        String favicon() {
            return "forward:/img/favicon.ico";
        }
    }
}
