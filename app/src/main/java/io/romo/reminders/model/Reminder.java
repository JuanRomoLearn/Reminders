package io.romo.reminders.model;

import java.util.Date;

public class Reminder {

    private int id;
    private String title;
    private String description;
    private Priority priority;
    private Date creationDate;

    public enum Priority {NONE, LOW, MEDIUM, HIGH}

    public Reminder() {
        // Default constructor
    }

    public Reminder(int id, String title, String description, Priority priority, Date creationDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
