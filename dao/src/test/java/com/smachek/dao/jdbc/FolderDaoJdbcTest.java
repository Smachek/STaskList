package com.smachek.dao.jdbc;

import com.smachek.dao.FolderDao;
import com.smachek.model.Folder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

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
}