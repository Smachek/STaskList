package com.smachek.dao.jdbc;

import com.smachek.dao.TaskDao;
import com.smachek.model.Task;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class TaskDaoJdbc implements TaskDao {
    private static final String SQL_GET_ALL_TASKS =
            "SELECT T.ID_TASK, T.ID_FOLDER, T.NAME_TASK, T.PRIORITY, T.DESCRIPTION, T.START_DATE, T.DUE_DATE, T.CREATE_DATE, T.DONE_MARK, T.DONE_DATE " +
                    "FROM TASK AS T ORDER BY T.ID_TASK";
    private static final String SQL_GET_TASK_BY_ID =
            "SELECT T.ID_TASK, T.ID_FOLDER, T.NAME_TASK, T.PRIORITY, T.DESCRIPTION, T.START_DATE, T.DUE_DATE, T.CREATE_DATE, T.DONE_MARK, T.DONE_DATE " +
                    "FROM TASK AS T WHERE T.ID_TASK = :in_ID_TASK";
    private static final String SQL_CREATE_TASK =
            "INSERT INTO TASK " +
                    "(ID_FOLDER, NAME_TASK, PRIORITY, DESCRIPTION, START_DATE, DUE_DATE, CREATE_DATE) " +
                    "VALUES(:in_ID_FOLDER, :in_NAME_TASK, :in_PRIORITY, :in_DESCRIPTION, :in_START_DATE, :in_DUE_DATE, :in_CREATE_DATE)";
    private static final String SQL_UPDATE_TASK =
            "UPDATE TASK " +
                    "SET ID_FOLDER = :in_ID_FOLDER, NAME_TASK = :in_NAME_TASK, PRIORITY = :in_PRIORITY, DESCRIPTION = :in_DESCRIPTION, START_DATE = :in_START_DATE, DUE_DATE = :in_DUE_DATE, DONE_MARK = :in_DONE_MARK, DONE_DATE = :in_DONE_DATE " +
                    "WHERE ID_TASK = :in_ID_TASK";
    private static final String SQL_DELETE_TASK =
            "DELETE FROM TASK WHERE ID_TASK = :in_ID_TASK";

    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    RowMapper rowMapper = BeanPropertyRowMapper.newInstance(Task.class);

    public TaskDaoJdbc(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }
    
    @Override
    public List<Task> findAll() {
        return namedParameterJdbcTemplate.query(SQL_GET_ALL_TASKS, rowMapper);
    }

    @Override
    public Optional<Task> findById(Integer idTask) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("in_ID_TASK", idTask);
        return Optional.ofNullable((Task) namedParameterJdbcTemplate.queryForObject(SQL_GET_TASK_BY_ID, sqlParameterSource, rowMapper));
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
        namedParameterJdbcTemplate.update(SQL_CREATE_TASK, sqlParameterSource, keyHolder);
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
        return namedParameterJdbcTemplate.update(SQL_UPDATE_TASK, sqlParameterSource);
    }

    @Override
    public Integer delete(Integer idTask) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("in_ID_TASK", idTask);
        return namedParameterJdbcTemplate.update (SQL_DELETE_TASK, sqlParameterSource);
    }

}
