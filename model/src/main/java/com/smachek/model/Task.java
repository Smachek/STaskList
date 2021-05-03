package com.smachek.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Objects;

public class Task {
    private Integer idTask;
    private Integer idFolder;
    private String nameTask;
    private Integer priority;
    private String description;
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date startDate;
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date dueDate;
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date createDate;
    private Boolean doneMark;
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date doneDate;

    public Task() {
        this.createDate = new Date();
        this.priority = 0;
    }

    public Task(Integer idFolder, String taskName) {
        this.idFolder = idFolder;
        this.nameTask = taskName;
        this.createDate = new Date();
        this.priority = 0;
    }

    public Integer getIdTask() {
        return idTask;
    }

    public void setIdTask(Integer idTask) {
        this.idTask = idTask;
    }

    public Integer getIdFolder() {
        return idFolder;
    }

    public void setIdFolder(Integer idFolder) {
        this.idFolder = idFolder;
    }

    public String getNameTask() {
        return nameTask;
    }

    public void setNameTask(String nameTask) {
        this.nameTask = nameTask;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Boolean getDoneMark() {
        return doneMark;
    }

    public void setDoneMark(Boolean doneMark) {
        this.doneMark = doneMark;
    }

    public Date getDoneDate() {
        return doneDate;
    }

    public void setDoneDate(Date doneDate) {
        this.doneDate = doneDate;
    }

    @Override
    public String toString() {
        return "Task{" +
                "idTask=" + idTask +
                ", idFolder=" + idFolder +
                ", nameTask='" + nameTask + '\'' +
                ", priority=" + priority +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", dueDate=" + dueDate +
                ", createDate=" + createDate +
                ", doneMark=" + doneMark +
                ", doneDate=" + doneDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return idTask.equals(task.idTask) && idFolder.equals(task.idFolder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTask);
    }
}
