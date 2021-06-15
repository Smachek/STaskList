package com.smachek.dao.jdbc;

import com.smachek.dao.FolderDao;
import com.smachek.model.Folder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class FolderDaoJdbc implements FolderDao {

    @Value("${folder.getAll}")
    private String getAllSql;

    @Value("${folder.getById}")
    private String getByIdSql;

    @Value("${folder.create}")
    private String createSql;

    @Value("${folder.checkNameExist}")
    private String checkNameExistSql;

    @Value("${folder.update}")
    private String updateSql;

    @Value("${folder.delete}")
    private String deleteSql;

    @Value("${folder.count}")
    private String countSql;

    @Value("${folder.countTasks}")
    private String countTasksSql;

    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    RowMapper rowMapper = BeanPropertyRowMapper.newInstance(Folder.class);

    public FolderDaoJdbc(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Folder> findAll() {
        return namedParameterJdbcTemplate.query(getAllSql, rowMapper);
    }

    @Override
    public Optional<Folder> findById(Integer idFolder) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("in_ID_FOLDER", idFolder);
        List<Folder> results = namedParameterJdbcTemplate.query(getByIdSql, sqlParameterSource, rowMapper);
        return Optional.ofNullable(DataAccessUtils.uniqueResult(results) );
    }

    private boolean isFolderNameUnique(Folder folder) {
        return namedParameterJdbcTemplate.queryForObject(checkNameExistSql,
                new MapSqlParameterSource("in_NAME_FOLDER", folder.getNameFolder()), Integer.class) == 0;
    }

    @Override
    public Integer create(Folder folder) {
        if (!isFolderNameUnique(folder)) {
            throw new IllegalArgumentException("Folder with the same name already exists in DB");
        }
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("in_NAME_FOLDER",folder.getNameFolder())
                .addValue("in_DESCRIPTION", folder.getDescription())
                .addValue("in_CREATE_DATE", folder.getCreateDate());
        namedParameterJdbcTemplate.update(createSql, sqlParameterSource, keyHolder);
        Integer idFolder = Objects.requireNonNull(keyHolder.getKey()).intValue();
        folder.setIdFolder(idFolder);
        return idFolder;
    }

    @Override
    public Integer update(Folder folder) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("in_ID_FOLDER", folder.getIdFolder())
                .addValue("in_NAME_FOLDER",folder.getNameFolder())
                .addValue("in_DESCRIPTION", folder.getDescription());
        return namedParameterJdbcTemplate.update(updateSql, sqlParameterSource);
    }

    @Override
    public Integer delete(Integer idFolder) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("in_ID_FOLDER", idFolder);
        return namedParameterJdbcTemplate.update (deleteSql, sqlParameterSource);
    }

    @Override
    public Integer count() {
        return namedParameterJdbcTemplate.queryForObject(countSql, new HashMap<>(), Integer.class);
    }

    @Override
    public Integer countTasks(Integer idFolder) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("in_ID_FOLDER", idFolder);
        return namedParameterJdbcTemplate.queryForObject(countTasksSql, sqlParameterSource, Integer.class);
    }
}
