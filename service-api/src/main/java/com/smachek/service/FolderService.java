package com.smachek.service;
import com.smachek.model.Folder;

import java.util.List;
import java.util.Optional;

public interface FolderService {

    List<Folder> findAll();
    Optional<Folder> findById(Integer idFolder);
    Integer create (Folder folder);
    Integer update (Folder folder);
    Integer delete (Integer idFolder);

    Integer count();
}

