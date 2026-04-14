package com.example.todo.service;

import com.example.todo.dto.TodoResponse;
import com.example.todo.entity.Todo;
import com.example.todo.exception.ResourceNotFoundException;
import com.example.todo.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)  // ① Mockito sans Spring
public class TodoServiceTest {

    @Mock
    TodoRepository todoRepository; // ② faux repository

    @InjectMocks
    TodoService todoService; // ③ le vrai service, avec le mock injecté
    @Test
    void findAll_shouldReturnAllTodos() {
        //GIVEN
        Todo todo = new Todo();
        todo.setId(1L);
        todo.setTitle("Apprendre Spring");
        todo.setCompleted(false);

        given(todoRepository.findAll()).willReturn(List.of(todo));

        //WHEN
        List<TodoResponse> result = todoService.getAllTodos();

        // THEN
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("Apprendre Spring");
        verify(todoRepository, times(1)).findAll();   // ④ vérifie l'appel
    }
    @Test
    void findById_whenNotFound_shouldThrowException() {
        // GIVEN
        given(todoRepository.findById(99L)).willReturn(Optional.empty());

        // WHEN / THEN
        assertThatThrownBy(() -> todoService.getTodoById(99L))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}


