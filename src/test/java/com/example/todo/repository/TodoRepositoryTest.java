package com.example.todo.repository;
import com.example.todo.entity.Todo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;


@DataJpaTest                          // ① charge uniquement JPA + H2, rollback automatique après chaque test
class TodoRepositoryTest {

    @Autowired
    TodoRepository todoRepository;   // ② le vrai repository, pas un mock

    @Test
    void save_shouldPersistTodo() {
        // GIVEN
        Todo todo = new Todo("Apprendre JPA", "Test persistence");

        // WHEN
        Todo saved = todoRepository.save(todo);

        // THEN
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getTitle()).isEqualTo("Apprendre JPA");
    }

    @Test
    void findById_shoulodReturnTdo() {
        // GIVEN
        Todo todo = new Todo("Test findById", "description");
        Todo saved = todoRepository.save(todo);

        // WHEN
        Optional<Todo> result = todoRepository.findById(saved.getId());

        // THEN
        assertThat(result).isPresent();
        assertThat(result.get().getTitle()).isEqualTo("Test findById");
    }

    @Test
    void findById_whenNotExists_shouldReturnEmpty() {
        // WHEN
        Optional<Todo> result = todoRepository.findById(999L);

        // THEN
        assertThat(result).isEmpty();
    }
}
