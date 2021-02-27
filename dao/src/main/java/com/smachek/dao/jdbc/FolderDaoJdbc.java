package com.smachek.dao.jdbc;

import com.smachek.dao.FolderDao;
import com.smachek.model.Folder;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class FolderDaoJdbc implements FolderDao {

    private static final String SQL_GET_ALL_FOLDERS =
            "SELECT F.ID_FOLDER, F.NAME_FOLDER, F.DESCRIPTION, F.CREATE_DATE FROM FOLDER AS F ORDER BY F.ID_FOLDER";

    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public FolderDaoJdbc(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Folder> findAll() {
        return namedParameterJdbcTemplate.query(SQL_GET_ALL_FOLDERS, new FolderRowMapper());
    }

    private class FolderRowMapper implements RowMapper<Folder> {
        @Override
        public Folder mapRow(ResultSet resultSet, int i) throws SQLException {
            Folder folder = new Folder();
            folder.setFolderId(resultSet.getInt("ID_FOLDER"));
            folder.setFolderName(resultSet.getString("NAME_FOLDER"));
            folder.setDescription (resultSet.getString("DESCRIPTION"));
            folder.setCreateDate(resultSet.getDate("CREATE_DATE"));
            return folder;
        }
    }
}
