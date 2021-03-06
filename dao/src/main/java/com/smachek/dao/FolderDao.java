package com.smachek.dao;

import com.smachek.model.Folder;

import java.util.List;
import java.util.Optional;

public interface FolderDao {

    List<Folder> findAll();
    Optional<Folder> findById(Integer idFolder);
    Integer create (Folder folder);
    Integer update (Folder folder);
    Integer delete (Integer idFolder);
}
