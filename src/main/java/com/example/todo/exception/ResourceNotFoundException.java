package com.example.todo.exception;

// exception/ResourceNotFoundException.java
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resource, Long id) {
        super(resource + " introuvable avec l'id : " + id);
    }
}
