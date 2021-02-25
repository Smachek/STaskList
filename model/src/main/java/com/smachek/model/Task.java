package com.smachek.model;

import java.util.Date;

public class Task {
    private Integer taskId;
    private Integer folderId;
    private String taskName;
    private Integer priority;
    private String description;
    private Date startDate;
    private Date dueDate;
    private Date createDate;
    private Boolean doneMark;
    private Date doneDate;

    public Task() {
    }

    public Task(Integer folderId, String taskName) {
        this.folderId = folderId;
        this.taskName = taskName;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getFolderId() {
        return folderId;
    }

    public void setFolderId(Integer folderId) {
        this.folderId = folderId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
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
                "taskId=" + taskId +
                ", folderId=" + folderId +
                ", taskName='" + taskName + '\'' +
                ", priority=" + priority +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", dueDate=" + dueDate +
                ", createDate=" + createDate +
                ", doneMark=" + doneMark +
                ", doneDate=" + doneDate +
                '}';
    }
}
