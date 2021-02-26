package com.smachek.dao;

import com.smachek.model.Folder;

import java.util.List;

public interface FolderDao {

    List<Folder> findAll();
}
