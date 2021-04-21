package com.smachek.dao.jdbc;

import com.smachek.dao.FolderDao;
import com.smachek.model.Folder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:test-dao.xml", "classpath*:dao.xml"})
public class FolderDaoJdbcIT {

    private static final Logger LOGGER = LoggerFactory.getLogger(FolderDaoJdbcIT.class);

    @Autowired
    private FolderDao folderDao;

    @Test
    public void findAllTest() {
        List<Folder> folders = folderDao.findAll();
        Assertions.assertNotNull(folders);
        Assertions.assertTrue(folders.size() > 0);
    }

    @Test
    public void findByIdTest(){
        List<Folder> folders = folderDao.findAll();
        Assertions.assertNotNull(folders);
        Assertions.assertTrue(folders.size() > 0);

        Integer idFolder = folders.get(0).getIdFolder();
        Folder actualFolder = folderDao.findById(idFolder).get();
        Assertions.assertEquals(folders.get(0), actualFolder);
    }

    @Test
    public void findByIdExceptionalTest(){
        Optional<Folder> optionalFolder = folderDao.findById(999);
        Assertions.assertTrue (optionalFolder.isEmpty());
    }

    @Test
    public void createFolderTest() {
        List<Folder> folders = folderDao.findAll();
        Assertions.assertNotNull(folders);
        Assertions.assertTrue(folders.size() > 0);

        folderDao.create(new Folder("FOLDER"));

        List<Folder> actualFolders = folderDao.findAll();
        Assertions.assertEquals(folders.size()+1, actualFolders.size());
    }

    @Test
    public void createFolderWithSameNameTest() {
        List<Folder> folders = folderDao.findAll();
        Assertions.assertNotNull(folders);
        Assertions.assertTrue(folders.size() > 0);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            folderDao.create(new Folder("FOLDER_SAME"));
            folderDao.create(new Folder("FOLDER_SAME"));
        });
    }

    @Test
    public void createFolderWithSameNameDiffCaseTest() {
        List<Folder> folders = folderDao.findAll();
        Assertions.assertNotNull(folders);
        Assertions.assertTrue(folders.size() > 0);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            folderDao.create(new Folder("FOLDER_SAME"));
            folderDao.create(new Folder("FOLDER_Same"));
        });
    }

    @Test
    public void updateFolderTest() {
        List<Folder> folders = folderDao.findAll();
        Assertions.assertNotNull(folders);
        Assertions.assertTrue(folders.size() > 0);

        Folder folder = folders.get(0);
        folder.setNameFolder("FOLDER_update");
        folder.setDescription("FOLDER_DESCRIPTION_update");
        folderDao.update(folder);

        Optional<Folder> actualFolder = folderDao.findById(folder.getIdFolder());
        Assertions.assertEquals("FOLDER_update", actualFolder.get().getNameFolder());
        Assertions.assertEquals("FOLDER_DESCRIPTION_update", actualFolder.get().getDescription());
    }

    @Test
    public void updateFolderWithSameNameTest() {
        List<Folder> folders = folderDao.findAll();
        Assertions.assertNotNull(folders);
        Assertions.assertTrue(folders.size() > 0);

        Folder folder = folders.get(0);
        folder.setNameFolder(folders.get(1).getNameFolder());
        folderDao.update(folder);
    }

    @Test
    public void deleteFolderTest() {
        Integer deleteIdFolder = folderDao.create(new Folder("FOLDER_DELETE"));
        List<Folder> folders = folderDao.findAll();

        int deletedCount = folderDao.delete(deleteIdFolder);

        List<Folder> actualFolders = folderDao.findAll();
        Assertions.assertEquals(1, deletedCount);
        Assertions.assertEquals(folders.size()-1, actualFolders.size());
    }

    @Test
    public void testLogging() {
        LOGGER.trace("Logging trace");
        LOGGER.debug("Logging debug");
        LOGGER.info("Logging info");
        LOGGER.warn("Logging warn");
        LOGGER.error("Logging error");
    }

}