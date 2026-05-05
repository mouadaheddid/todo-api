package com.example.todo.dto;

import java.time.LocalDateTime;

// DTO de sortie — ce que l'API renvoie
// Ici on inclut id et createdAt car le client en a besoin
public class TodoResponse {

    private Long id;
    private String title;
    private String description;
    private boolean completed;
    private Integer priority;
    private LocalDateTime createdAt;

    public TodoResponse() {}

    public TodoResponse(Long id, String title, String description,
                        boolean completed, Integer priority, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.completed = completed;
        this.priority = priority;
        this.createdAt = createdAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
