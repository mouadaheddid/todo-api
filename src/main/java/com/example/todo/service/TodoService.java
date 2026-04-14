package com.example.todo.service;


import com.example.todo.controller.TodoController;
import com.example.todo.dto.TodoRequest;
import com.example.todo.dto.TodoResponse;
import com.example.todo.entity.Todo;
import com.example.todo.exception.ResourceNotFoundException;
import com.example.todo.mapper.TodoMapper;
import com.example.todo.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

// Couche service - contient toute la logique metier
@Service
public class TodoService {
    private final  TodoRepository todoRepository;

    // Injection par constructeur
    // "final" garantit que le repository ne peut pas être changé après construction

    public TodoService(TodoRepository todoRepository){
         this.todoRepository = todoRepository;
     }

     // ===== RÉCUPÉRER TOUS LES TODOS =====
     public List<TodoResponse> getAllTodos(){
         return todoRepository.findAll()
                 .stream()
                 .map(TodoMapper::toResponse) // Conversion Entity -> DTO
                 .collect(Collectors.toList());
     }

     // Récupérer un todo par son id
     // Lance une exception si l'id n'existe pas
    public TodoResponse getTodoById(Long id){
          Todo todo =todoRepository.findById(id)
                  // orElseThrow : si l'id n'existe pas → exception
                  // On améliorera ça au Jour 4 avec une exception custom
                .orElseThrow(() -> new ResourceNotFoundException("Todo" ,id));
          return TodoMapper.toResponse(todo);
    }

    // ===== CRÉER UN TODO =====
    public TodoResponse createTodo(TodoRequest request) {
        Todo todo = TodoMapper.toEntity(request);       // DTO → Entity
        Todo saved = todoRepository.save(todo);         // Sauvegarde en base
        return TodoMapper.toResponse(saved);            // Entity → DTO
    }

    // Mettre à jour un todo existant
    public TodoResponse updateTodo(Long id, TodoRequest request) {
        // D'abord vérifier que le todo existe
        Todo existing = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found with id: " + id));

        // Mettre à jour les champs (on ne touche pas à id ni createdAt)
        existing.setTitle(request.getTitle());
        existing.setDescription(request.getDescription());
        existing.setCompleted(request.isCompleted());

        Todo updated = todoRepository.save(existing);
        return TodoMapper.toResponse(updated);
    }

    // ===== SUPPRIMER UN TODO =====
    public void deleteTodo(Long id) {
        // Vérifier l'existence avant de supprimer
        if (!todoRepository.existsById(id)) {
            throw new RuntimeException("Todo not found with id: " + id);
        }
        todoRepository.deleteById(id);
    }

    // Filtrer par statut (completed = true ou false)
    public List<TodoResponse> getTodosByStatus(boolean completed) {
        return todoRepository.findByCompleted(completed)
                .stream()
                .map(TodoMapper::toResponse)
                .collect(Collectors.toList());
    }
}
