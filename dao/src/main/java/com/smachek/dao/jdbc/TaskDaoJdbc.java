package com.smachek.dao.jdbc;

import com.smachek.dao.TaskDao;
import com.smachek.model.Task;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class TaskDaoJdbc implements TaskDao {

    @Value("${task.getAll}")
    private String getAllSql;

    @Value("${task.getById}")
    private String getByIdSql;

    @Value("${task.getByIdFolder}")
    private String getByIdFolderSql;

    @Value("${task.searchByText}")
    private String searchByTextSql;

    @Value("${task.create}")
    private String createSql;

    @Value("${task.update}")
    private String updateSql;

    @Value("${task.delete}")
    private String deleteSql;

    @Value("${task.count}")
    private String countSql;

    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    RowMapper rowMapper = BeanPropertyRowMapper.newInstance(Task.class);

    public TaskDaoJdbc(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }
    
    @Override
    public List<Task> findAll() {
        return namedParameterJdbcTemplate.query(getAllSql, rowMapper);
    }

    @Override
    public Optional<Task> findById(Integer idTask) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("in_ID_TASK", idTask);
        List<Task> results = namedParameterJdbcTemplate.query(getByIdSql, sqlParameterSource, rowMapper);
        return Optional.ofNullable(DataAccessUtils.uniqueResult(results) );
    }

    @Override
    public List<Task> findByFolder(Integer idFolder) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("in_ID_FOLDER", idFolder);
        return namedParameterJdbcTemplate.query(getByIdFolderSql, sqlParameterSource, rowMapper);
    }

    @Override
    public List<Task> findText(String search) {
        String searchLike= "%" + search.trim() + "%";
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("in_SEARCH_TEXT", searchLike);
        return namedParameterJdbcTemplate.query(searchByTextSql, sqlParameterSource, rowMapper);
    }

    @Override
    public Integer create(Task task) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("in_ID_FOLDER", task.getIdFolder())
                .addValue("in_NAME_TASK", task.getNameTask())
                .addValue("in_PRIORITY", task.getPriority())
                .addValue("in_DESCRIPTION", task.getDescription())
                .addValue("in_START_DATE", task.getStartDate())
                .addValue("in_DUE_DATE", task.getDueDate())
                .addValue("in_CREATE_DATE", task.getCreateDate());
        namedParameterJdbcTemplate.update(createSql, sqlParameterSource, keyHolder);
        Integer idTask = Objects.requireNonNull(keyHolder.getKey()).intValue();
        task.setIdTask(idTask);
        return idTask;
    }

    @Override
    public Integer update(Task task) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("in_ID_TASK", task.getIdTask())
                .addValue("in_ID_FOLDER", task.getIdFolder())
                .addValue("in_NAME_TASK", task.getNameTask())
                .addValue("in_PRIORITY", task.getPriority())
                .addValue("in_DESCRIPTION", task.getDescription())
                .addValue("in_START_DATE", task.getStartDate())
                .addValue("in_DUE_DATE", task.getDueDate())
                .addValue("in_DONE_MARK", task.getDoneMark())
                .addValue("in_DONE_DATE", task.getDoneDate());
        return namedParameterJdbcTemplate.update(updateSql, sqlParameterSource);
    }

    @Override
    public Integer delete(Integer idTask) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("in_ID_TASK", idTask);
        return namedParameterJdbcTemplate.update (deleteSql, sqlParameterSource);
    }

    @Override
    public Integer count() {
        return namedParameterJdbcTemplate.queryForObject(countSql, new HashMap<>(), Integer.class);
    }

}
