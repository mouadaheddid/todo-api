package com.example.todo.controller;

import com.example.todo.dto.TodoResponse;
import com.example.todo.service.TodoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TodoController.class)      // ① charge uniquement la couche web
public class TodoControllerTest {
    @Autowired
    MockMvc mockMvc;           // ② simule les requêtes HTTP, pas de vrai serveur


    @MockitoBean
    TodoService todoService;     // ③ MockBean et pas Mock — injecté dans le contexte Spring

    @Test
    @WithMockUser                 // ④ simule un user connecté pour passer la security
    void getAllTodos_shouldReturn200() throws Exception {
        // GIVEN
        TodoResponse response = new TodoResponse();
        response.setTitle("Apprendre Spring");
        response.setCompleted(false);

        given(todoService.getAllTodos()).willReturn(List.of(response));

        // WHEN + THEN
        mockMvc.perform(get("/api/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Apprendre Spring"));
    }

    @Test
    @WithMockUser
    void getTodoById_whenNotFound_shouldReturn404() throws Exception {
        // GIVEN
        given(todoService.getTodoById(99L))
                .willThrow(new RuntimeException("Not found"));

        // WHEN + THEN
        mockMvc.perform(get("/api/todos/99"))
                .andExpect(status().isNotFound());
    }
}
