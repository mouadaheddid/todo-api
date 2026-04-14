package com.example.todo.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "todos")
public class Todo {

    // Variables
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private boolean completed = false;
    // Date de création, remplie automatiquement avant l'insertion
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt ;

    // Callback JPA : remplit createdAt juste avant le premier INSERT
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

     //  *******   Constructeurs   *******
    public Todo (){};
    public Todo(String title, String description){
        this.title = title;
        this.description = description;

    }

    // ********   Getters & Setters  *******
    public Long getId(){
        return id;
    }
    // Titre de la tache est obligatoire
    @Column(nullable = false)
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
