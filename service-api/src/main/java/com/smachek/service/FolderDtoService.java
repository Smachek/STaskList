package com.smachek.service;

import com.smachek.model.dto.FolderDto;

import java.util.List;

public interface FolderDtoService {
    List<FolderDto> findAllWithTaskCount();
}
