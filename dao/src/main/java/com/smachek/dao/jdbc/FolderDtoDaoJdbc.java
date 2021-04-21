package com.smachek.dao.jdbc;

import com.smachek.dao.FolderDtoDao;
import com.smachek.model.dto.FolderDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FolderDtoDaoJdbc implements FolderDtoDao {

    @Value("${folderDto.getAllWithTaskCount}")
    private String getAllWithTaskCountSql;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(FolderDtoDaoJdbc.class);

    public FolderDtoDaoJdbc(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<FolderDto> findAllWithTaskCount() {
        LOGGER.debug("findAllWithTaskCount()");
        List<FolderDto> folders = namedParameterJdbcTemplate.query(getAllWithTaskCountSql,
                BeanPropertyRowMapper.newInstance(FolderDto.class));
        return folders;
    }
}
