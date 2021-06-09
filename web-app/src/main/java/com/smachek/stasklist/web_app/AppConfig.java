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
@EnableWebMvc
@ComponentScan(basePackages = "com.smachek.*")
@Import(TestDbConfiguration.class)
@PropertySource({"classpath:dao.properties","classpath:application.properties"})
public class AppConfig implements WebMvcConfigurer {

    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/resources/");
    }

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

    // Thymeleaf
    @Bean
    public SpringResourceTemplateResolver templateResolver(){
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        //templateResolver.setApplicationContext(this.applicationContext);
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");
        // Template cache is true by default. Set to false if you want
        // templates to be automatically updated when modified.
        templateResolver.setCacheable(false);
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine(){
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.setEnableSpringELCompiler(true);
        return templateEngine;
    }

    @Bean
    public ThymeleafViewResolver viewResolver(){
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine());
        // NOTE 'order' and 'viewNames' are optional
        viewResolver.setOrder(1);
        viewResolver.setViewNames(new String[] {"*"});
        viewResolver.setCharacterEncoding("UTF-8");
        return viewResolver;
    }

    @Controller
    static class FaviconController {
        @RequestMapping("favicon.ico")
        String favicon() {
            return "forward:/resources/static/img/favicon.ico";
        }
    }
}
