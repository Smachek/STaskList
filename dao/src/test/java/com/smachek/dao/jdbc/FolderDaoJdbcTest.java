package com.smachek.dao.jdbc;

import com.smachek.dao.FolderDao;
import com.smachek.model.Folder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:test-dao.xml"})
public class FolderDaoJdbcTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(FolderDaoJdbcTest.class);

    @Autowired
    private FolderDao folderDao;

    @Test
    public void findAllTest() {
        List<Folder> folders = folderDao.findAll();
        Assert.assertNotNull(folders);
        Assert.assertTrue(folders.size() > 0);
    }

    @Test
    public void findByIdTest(){
        List<Folder> folders = folderDao.findAll();
        Assert.assertNotNull(folders);
        Assert.assertTrue(folders.size() > 0);

        Integer idFolder = folders.get(0).getIdFolder();
        Folder actualFolder = folderDao.findById(idFolder).get();
        Assert.assertEquals(folders.get(0), actualFolder);
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void findByIdExceptionalTest(){
        folderDao.findById(999).get();
    }

    @Test
    public void createFolderTest() {
        List<Folder> folders = folderDao.findAll();
        Assert.assertNotNull(folders);
        Assert.assertTrue(folders.size() > 0);

        Folder folder = new Folder("Test Folder");
        folderDao.create(folder);

        List<Folder> actualFolders = folderDao.findAll();
        Assert.assertEquals(folders.size()+1, actualFolders.size());
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