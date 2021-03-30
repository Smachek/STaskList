package com.smachek.service.impl;

import com.smachek.dao.FolderDtoDao;
import com.smachek.model.dto.FolderDto;
import com.smachek.service.FolderDtoService;

import java.util.List;

public class FolderDtoServiceImpl implements FolderDtoService {

    private final FolderDtoDao folderDtoDao;

    public FolderDtoServiceImpl(FolderDtoDao folderDtoDao) {
        this.folderDtoDao = folderDtoDao;
    }

    @Override
    public List<FolderDto> findAllWithTaskCount() {
        return null;
    }
}
