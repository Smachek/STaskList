package com.smachek.dao.jdbc;

import com.smachek.dao.FolderDao;
import com.smachek.model.Folder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:test-dao.xml"})
public class FolderDaoJdbcTest {

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

        folderDao.create(new Folder("FOLDER"));

        List<Folder> actualFolders = folderDao.findAll();
        Assert.assertEquals(folders.size()+1, actualFolders.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void createFolderWithSameNameTest() {
        List<Folder> folders = folderDao.findAll();
        Assert.assertNotNull(folders);
        Assert.assertTrue(folders.size() > 0);

        folderDao.create(new Folder("FOLDER_SAME"));
        folderDao.create(new Folder("FOLDER_SAME"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void createFolderWithSameNameDiffCaseTest() {
        List<Folder> folders = folderDao.findAll();
        Assert.assertNotNull(folders);
        Assert.assertTrue(folders.size() > 0);

        folderDao.create(new Folder("FOLDER_SAME"));
        folderDao.create(new Folder("FOLDER_Same"));
    }

    @Test
    public void updateFolderTest() {
        List<Folder> folders = folderDao.findAll();
        Assert.assertNotNull(folders);
        Assert.assertTrue(folders.size() > 0);

        Folder folder = folders.get(0);
        folder.setNameFolder("FOLDER_update");
        folder.setDescription("FOLDER_DESCRIPTION_update");
        folderDao.update(folder);

        Optional<Folder> actualFolder = folderDao.findById(folder.getIdFolder());
        Assert.assertEquals("FOLDER_update", actualFolder.get().getNameFolder());
        Assert.assertEquals("FOLDER_DESCRIPTION_update", actualFolder.get().getDescription());
    }

    @Test
    public void updateFolderWithSameNameTest() {
        List<Folder> folders = folderDao.findAll();
        Assert.assertNotNull(folders);
        Assert.assertTrue(folders.size() > 0);

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
        Assert.assertEquals(1, deletedCount);
        Assert.assertEquals(folders.size()-1, actualFolders.size());
    }
}