package com.example.todo.controller;

import com.example.todo.dto.TodoRequest;
import com.example.todo.dto.TodoResponse;
import com.example.todo.service.TodoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.security.core.Authentication;

@RestController                  // Combine @Controller + @ResponseBody
@RequestMapping("/api/todos")    // Préfixe commun : tous les endpoints commencent par /api/todos
public class TodoController {

    private final TodoService todoService;

    // Injection du Service (pas du Repository !)
    // Le Controller ne parle JAMAIS directement au Repository
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    // GET /api/todos          → tous les todos
    // GET /api/todos?completed=true  → filtrés par statut
    @GetMapping
    public ResponseEntity<List<TodoResponse>> getAllTodos(
            @RequestParam(required = false) Boolean completed) {

        List<TodoResponse> todos;

        if (completed != null) {
            todos = todoService.getTodosByStatus(completed);
        } else {
            todos = todoService.getAllTodos();
        }

        return ResponseEntity.ok(todos);  // 200 OK
    }

    // GET /api/todos/3 → le todo avec id=3
    @GetMapping("/{id}")
    public ResponseEntity<TodoResponse> getTodoById(@PathVariable Long id) {
        TodoResponse todo = todoService.getTodoById(id);
        return ResponseEntity.ok(todo);  // 200 OK
    }
    @GetMapping("/me")
    public ResponseEntity<String> whoAmI(Authentication authentication) {
        String username = authentication.getName();
        String roles = authentication.getAuthorities().toString();
        return ResponseEntity.ok("Connecté  : " + username + " | Rôles  : " + roles);  // 200 OK
    }

    // POST /api/todos → créer un nouveau todo
    @PostMapping
    public ResponseEntity<TodoResponse> createTodo(@Valid @RequestBody TodoRequest request) {
        // Si @Valid échoue → MethodArgumentNotValidException automatique
        TodoResponse created = todoService.createTodo(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);  // 201 Created
    }

    // PUT /api/todos/3 → modifier le todo avec id=3
    @PutMapping("/{id}")
    public ResponseEntity<TodoResponse> updateTodo(
            @PathVariable Long id,
            @Valid @RequestBody TodoRequest request) {

        TodoResponse updated = todoService.updateTodo(id, request);
        return ResponseEntity.ok(updated);  // 200 OK
    }

    // DELETE /api/todos/3 → supprimer le todo avec id=3
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
        return ResponseEntity.noContent().build();  // 204 No Content
    }

}