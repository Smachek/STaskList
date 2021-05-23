package com.smachek.service.impl;

import com.smachek.dao.FolderDao;
import com.smachek.model.Folder;
import com.smachek.service.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FolderServiceImpl implements FolderService {

    //private static final Logger LOGGER = LoggerFactory.getLogger(FolderServiceImpl.class);

    private final FolderDao folderDao;

    @Autowired
    public FolderServiceImpl(FolderDao folderDao) {
        this.folderDao = folderDao;
    }

    @Override
    public List<Folder> findAll() {
        return folderDao.findAll();
    }

    @Override
    public Optional<Folder> findById(Integer id) {
        return folderDao.findById(id);
    }

    @Override
    public Integer create(Folder folder) {
        return folderDao.create(folder);
    }

    @Override
    public Integer update(Folder folder) {
        return folderDao.update(folder);
    }

    @Override
    public Integer delete(Integer idFolder) {
        return folderDao.delete(idFolder);
    }

    @Override
    public Integer count() {
        return folderDao.count();
    }

    @Override
    public Integer countTasks(Integer idFolder) {
        return folderDao.countTasks(idFolder);
    }
}
