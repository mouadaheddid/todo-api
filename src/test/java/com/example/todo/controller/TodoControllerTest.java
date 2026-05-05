package com.example.todo.controller;

import com.example.todo.dto.TodoRequest;
import com.example.todo.dto.TodoResponse;
import com.example.todo.exception.ResourceNotFoundException;
import com.example.todo.service.TodoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDateTime;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TodoController.class)      // ① charge uniquement la couche web
public class TodoControllerTest {
    @Autowired
    MockMvc mockMvc;           // ② simule les requêtes HTTP, pas de vrai serveur

    @Autowired
    ObjectMapper objectMapper; // sérialise les payloads JSON pour les POST/PUT


    @MockitoBean
    TodoService todoService;     // ③ MockBean et pas Mock — injecté dans le contexte Spring

    @Test
    @WithMockUser                 // ④ simule un user connecté pour passer la security
    void getAllTodos_shouldReturn200() throws Exception {
        // GIVEN
        TodoResponse response = new TodoResponse();
        response.setTitle("Apprendre Spring");
        response.setCompleted(false);
        response.setPriority(2);

        given(todoService.getAllTodos()).willReturn(List.of(response));

        // WHEN + THEN
        mockMvc.perform(get("/api/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Apprendre Spring"))
                .andExpect(jsonPath("$[0].priority").value(2));
    }

    @Test
    @WithMockUser
    void getTodoById_whenNotFound_shouldReturn404() throws Exception {
        // GIVEN
        given(todoService.getTodoById(99L))
                .willThrow(new ResourceNotFoundException("Todo", 99L));

        // WHEN + THEN
        mockMvc.perform(get("/api/todos/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void createTodo_withValidPriority_shouldPersistAndReturnPriority() throws Exception {
        // GIVEN
        TodoRequest request = new TodoRequest();
        request.setTitle("Tache prioritaire");
        request.setPriority(2);

        TodoResponse saved = new TodoResponse(
                1L, "Tache prioritaire", null, false, 2, LocalDateTime.now());
        given(todoService.createTodo(any(TodoRequest.class))).willReturn(saved);

        // WHEN + THEN
        mockMvc.perform(post("/api/todos")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.priority").value(2));
    }

    @Test
    @WithMockUser
    void createTodo_withPriorityOutOfRange_shouldReturn400() throws Exception {
        // GIVEN — priority=5 viole @Max(3) sur TodoRequest
        TodoRequest request = new TodoRequest();
        request.setTitle("Tache invalide");
        request.setPriority(5);

        // WHEN + THEN
        mockMvc.perform(post("/api/todos")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void createTodo_withoutPriority_shouldReturn400() throws Exception {
        // GIVEN — priority absent viole @NotNull sur TodoRequest
        TodoRequest request = new TodoRequest();
        request.setTitle("Tache sans priorite");

        // WHEN + THEN
        mockMvc.perform(post("/api/todos")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
