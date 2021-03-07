package com.smachek.dao.jdbc;

import com.smachek.dao.FolderDao;
import com.smachek.model.Folder;
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

public class FolderDaoJdbc implements FolderDao {

    private static final String SQL_GET_ALL_FOLDERS =
            "SELECT F.ID_FOLDER, F.NAME_FOLDER, F.DESCRIPTION, F.CREATE_DATE FROM FOLDER AS F ORDER BY F.ID_FOLDER";
    private static final String SQL_GET_FOLDER_BY_ID =
            "SELECT F.ID_FOLDER, F.NAME_FOLDER, F.DESCRIPTION, F.CREATE_DATE FROM FOLDER AS F WHERE F.ID_FOLDER = :in_ID_FOLDER";
    private static final String SQL_CREATE_FOLDER =
            "INSERT INTO FOLDER (NAME_FOLDER, DESCRIPTION, CREATE_DATE) VALUES (:in_NAME_FOLDER, :in_DESCRIPTION, :in_CREATE_DATE)";
    private static final String SQL_CHECK_FOLDER_NAME_EXIST =
            "SELECT COUNT(NAME_FOLDER) FROM FOLDER WHERE LOWER(NAME_FOLDER) = LOWER(:in_NAME_FOLDER)";
    private static final String SQL_UPDATE_FOLDER =
            "UPDATE FOLDER SET NAME_FOLDER = :in_NAME_FOLDER, DESCRIPTION = :in_DESCRIPTION " +
                    "WHERE ID_FOLDER = :in_ID_FOLDER AND LOWER(:in_NAME_FOLDER) NOT IN (SELECT LOWER(NAME_FOLDER) FROM FOLDER)";
    private static final String SQL_DELETE_FOLDER =
            "DELETE FROM FOLDER WHERE ID_FOLDER = :in_ID_FOLDER";

    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    RowMapper rowMapper = BeanPropertyRowMapper.newInstance(Folder.class);

    public FolderDaoJdbc(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Folder> findAll() {
        return namedParameterJdbcTemplate.query(SQL_GET_ALL_FOLDERS, rowMapper);
    }

    @Override
    public Optional<Folder> findById(Integer idFolder) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("in_ID_FOLDER", idFolder);
        return Optional.ofNullable((Folder) namedParameterJdbcTemplate.queryForObject(SQL_GET_FOLDER_BY_ID, sqlParameterSource, rowMapper));
    }

    private boolean isFolderNameUnique(Folder folder) {
        return namedParameterJdbcTemplate.queryForObject(SQL_CHECK_FOLDER_NAME_EXIST,
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
        namedParameterJdbcTemplate.update(SQL_CREATE_FOLDER, sqlParameterSource, keyHolder);
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
        return namedParameterJdbcTemplate.update(SQL_UPDATE_FOLDER, sqlParameterSource);
    }

    @Override
    public Integer delete(Integer idFolder) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("in_ID_FOLDER", idFolder);
        return namedParameterJdbcTemplate.update (SQL_DELETE_FOLDER, sqlParameterSource);
    }
}
