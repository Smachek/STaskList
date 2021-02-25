package com.smachek.model;

import java.util.Date;

public class Folder {
    private Integer folderId;
    private String folderName;
    private String description;
    private Date createDate;

    public Folder() {
    }

    public Folder(String folderName) {
        this.folderName = folderName;
    }

    public Integer getFolderId() {
        return folderId;
    }

    public void setFolderId(Integer folderId) {
        this.folderId = folderId;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "Folder{" +
                "folderId=" + folderId +
                ", folderName='" + folderName + '\'' +
                ", description='" + description + '\'' +
                ", createDate=" + createDate +
                '}';
    }
}
