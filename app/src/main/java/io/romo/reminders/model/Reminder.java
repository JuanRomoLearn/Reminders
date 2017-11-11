package io.romo.reminders.model;

import java.util.Date;

public class Reminder {

    private int id;
    private String title;
    private String description;
    private Priority priority;
    private boolean completed;
    private Date creationDate;

    public enum Priority {NONE, LOW, MEDIUM, HIGH}

    public Reminder(String title, Priority priority, boolean completed) {
        this.title = title;
        this.priority = priority;
        this.completed = completed;
        this.creationDate = new Date(System.currentTimeMillis());
    }

    public Reminder(int id, String title, String description, Priority priority, boolean completed,
                    Date creationDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.completed = completed;
        this.creationDate = creationDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
