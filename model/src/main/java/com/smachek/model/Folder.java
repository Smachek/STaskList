package com.smachek.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Objects;

public class Folder {
    private Integer idFolder;
    private String nameFolder;
    private String description;
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date createDate;

    public Folder() {
        this.createDate = new Date();
    }

    public Folder(String nameFolder) {
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

    @Override
    public String toString() {
        return "Folder{" +
                "idFolder=" + idFolder +
                ", nameFolder='" + nameFolder + '\'' +
                ", description='" + description + '\'' +
                ", createDate=" + createDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Folder folder = (Folder) o;
        return idFolder.equals(folder.idFolder) && nameFolder.equals(folder.nameFolder) && Objects.equals(description, folder.description) && createDate.equals(folder.createDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idFolder);
    }
}
