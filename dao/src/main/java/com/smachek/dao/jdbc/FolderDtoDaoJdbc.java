package com.smachek.dao.jdbc;

import com.smachek.dao.FolderDtoDao;
import com.smachek.model.dto.FolderDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FolderDtoDaoJdbc implements FolderDtoDao {
    private static final String SQL_GET_ALL_FOLDERS_WITH_TASK_COUNT =
            "SELECT F.ID_FOLDER, F.NAME_FOLDER, F.DESCRIPTION, F.CREATE_DATE, COUNT(T.ID_TASK) AS TASK_COUNT " +
                    "FROM FOLDER AS F " +
                    "LEFT JOIN TASK AS T ON T.ID_FOLDER = F.ID_FOLDER " +
                    "GROUP BY F.ID_FOLDER, F.NAME_FOLDER, F.DESCRIPTION, F.CREATE_DATE " +
                    "ORDER BY F.ID_FOLDER ";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(FolderDtoDaoJdbc.class);

    public FolderDtoDaoJdbc(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<FolderDto> findAllWithTaskCount() {
        LOGGER.debug("findAllWithTaskCount()");
        List<FolderDto> folders = namedParameterJdbcTemplate.query(SQL_GET_ALL_FOLDERS_WITH_TASK_COUNT,
                BeanPropertyRowMapper.newInstance(FolderDto.class));
        return folders;
    }
}
