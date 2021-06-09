package com.smachek.dao.jdbc;

import com.smachek.dao.FolderDao;
import com.smachek.dao.TaskDao;
import com.smachek.stasklist.testdb.TestDbConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@Configuration
@Import(TestDbConfiguration.class)
public class TestDaoConfiguration {

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource){
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    public FolderDao folderDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        return new FolderDaoJdbc(namedParameterJdbcTemplate);
    }

    @Bean
    public TaskDao taskDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        return new TaskDaoJdbc(namedParameterJdbcTemplate);
    }
}
