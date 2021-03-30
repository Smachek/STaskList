package com.smachek.model.dto;

import java.util.Date;
import java.util.Objects;

public class FolderDto {
    private Integer idFolder;
    private String nameFolder;
    private String description;
    private Date createDate;
    private Integer taskCount;

    public FolderDto() {
    }

    public FolderDto(String nameFolder) {
        this.nameFolder = nameFolder;
        this.createDate = new Date();
    }

    public Integer getIdFolder() {
        return idFolder;
    }

    public void setIdFolder(Integer idFolder) {
        this.idFolder = idFolder;
    }

    public String getNameFolder() {
        return nameFolder;
    }

    public void setNameFolder(String nameFolder) {
        this.nameFolder = nameFolder;
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

    public Integer getTaskCount() {
        return taskCount;
    }

    public void setTaskCount(Integer taskCount) {
        this.taskCount = taskCount;
    }

    @Override
    public String toString() {
        return "FolderDto{" +
                "idFolder=" + idFolder +
                ", nameFolder='" + nameFolder + '\'' +
                ", description='" + description + '\'' +
                ", createDate=" + createDate +
                ", taskCount=" + taskCount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FolderDto folderDto = (FolderDto) o;
        return idFolder.equals(folderDto.idFolder) && nameFolder.equals(folderDto.nameFolder) && Objects.equals(description, folderDto.description) && createDate.equals(folderDto.createDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idFolder);
    }
}
