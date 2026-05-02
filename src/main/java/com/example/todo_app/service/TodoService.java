package com.example.todo_app.service;

import com.example.todo_app.model.Todo;
import com.example.todo_app.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<Todo> findAll() {
        return todoRepository.findAll();
    }

    public void add(String title,java.time.LocalDate dueDate) {
        Todo todo = new Todo();
        todo.setTitle(title);
        todo.setDueDate(dueDate);
        todoRepository.save(todo);
    }

    public void toggleComplete(Long id) {
        Todo todo = todoRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Todo not found: " + id));
        todo.setCompleted(!todo.isCompleted());
        todoRepository.save(todo);
    }

    public void delete(Long id) {
        todoRepository.deleteById(id);
    }
}