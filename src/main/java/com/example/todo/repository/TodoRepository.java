package com.example.todo.repository;

import com.example.todo.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository  extends JpaRepository<Todo, Long> {
    // SELECT * FROM todos WHERE completed = ?
    List<Todo> findByCompleted(boolean completed);

    // SELECT * FROM todos WHERE title LIKE '%keyword%' (insensible à la casse)
    List<Todo> findByTitleContainingIgnoreCase(String keyword);
}
