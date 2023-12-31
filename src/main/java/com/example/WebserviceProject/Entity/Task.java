package com.example.WebserviceProject.Entity;

import jakarta.persistence.*;

@Entity
//@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String task;
    private String assignedTo;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private User user;

    // Constructor med uppgiftsbeskrivning och tilldelad User som parameter
    public Task(String task, String assignedTo) {
        this.task = task;
        this.assignedTo = assignedTo;
    }

    // Tom Constructor
    public Task() {
    }

    // Getter & Setter för User, Task, Task-ID samt vem som är tilldelad tasks
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTask() {
        return task;
    }
    public void setTask(String task) {
        this.task = task;
    }
    public String getAssignedTo() {
        return assignedTo;
    }
    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }
}
