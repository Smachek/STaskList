package com.smachek.dao;

import com.smachek.model.dto.FolderDto;

import java.util.List;

public interface FolderDtoDao {
    List<FolderDto> findAllWithTaskCount();
}
