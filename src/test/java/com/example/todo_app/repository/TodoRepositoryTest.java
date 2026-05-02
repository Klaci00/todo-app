package com.example.todo_app.repository;

import com.example.todo_app.model.Todo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TodoRepositoryTest {

    @Autowired
    private TodoRepository todoRepository;

    @Test
    void shouldSaveAndFindTodo() {
        Todo todo = new Todo();
        todo.setTitle("Buy groceries");

        todoRepository.save(todo);
        List<Todo> todos = todoRepository.findAll();

        assertEquals(1, todos.size());
        assertEquals("Buy groceries", todos.get(0).getTitle());
        assertFalse(todos.get(0).isCompleted());
    }

    @Test
    void shouldDeleteTodo() {
        Todo todo = new Todo();
        todo.setTitle("Delete me");
        todoRepository.save(todo);
        Long id = todo.getId();

        todoRepository.deleteById(id);
        Optional<Todo> result = todoRepository.findById(id);

        assertTrue(result.isEmpty());
    }

    @Test
    void savedTodo_shouldHaveCreatedAtSetAutomatically() {
        Todo todo = new Todo();
        todo.setTitle("Timestamped task");
        todoRepository.save(todo);

        Todo found = todoRepository.findAll().get(0);
        assertNotNull(found.getCreatedAt());
    }
}