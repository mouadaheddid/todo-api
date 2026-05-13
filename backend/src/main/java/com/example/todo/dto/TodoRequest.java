package com.example.todo.dto;


import jakarta.validation.constraints.*;

// DTO d'entrée — utilisé dans POST et PUT
// Pas d'id (c'est le serveur qui le génère)
// Pas de createdAt (c'est automatique via @PrePersist)
public class TodoRequest {
    @NotBlank(message = "Le titre est obligatoire")
    @Size(min = 2, max = 100, message = "Entrer 2 et 100 caracteres")
    private String title;
    @Size(max = 500, message = "Description trop longue")
    private String description;

    @NotNull(message = "La priorite est obligatoire")
    @Min(value = 1, message = "Priorite minimale : 1")
    @Max(value = 3, message = "Priorite maximale : 3")
    Integer priority ;

    private boolean completed;

    // Constructeur vide (obligatoire pour la désérialisation JSON)
    public TodoRequest() {}

    public TodoRequest(String title, String description, boolean completed) {
        this.title = title;
        this.description = description;
        this.completed = completed;
    }

    // Ajoute ces deux méthodes
    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
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

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
