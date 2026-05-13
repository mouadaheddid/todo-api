package com.example.todo.mapper;

import com.example.todo.dto.TodoRequest;
import com.example.todo.dto.TodoResponse;
import com.example.todo.entity.Todo;

// Classe utilitaire — que des méthodes statiques
// Fait la conversion Entity <-> DTO
public class TodoMapper {

    // Constructeur privé : empêche l'instanciation
    // C'est une convention pour les classes utilitaires
    private TodoMapper() {}

    // DTO Request → Entity
    // Utilisé quand le client envoie des données (POST / PUT)
    public static Todo toEntity(TodoRequest request) {
        Todo todo = new Todo();
        todo.setTitle(request.getTitle());
        todo.setDescription(request.getDescription());
        todo.setCompleted(request.isCompleted());
        todo.setPriority(request.getPriority());
        // On ne set PAS id ni createdAt → gérés par JPA
        return todo;
    }

    // Entity → DTO Response
    // Utilisé quand on renvoie des données au client
    public static TodoResponse toResponse(Todo todo) {
        return new TodoResponse(
                todo.getId(),
                todo.getTitle(),
                todo.getDescription(),
                todo.isCompleted(),
                todo.getPriority(),
                todo.getCreatedAt()
        );
    }
}