package com.example.todo_app.service;

import com.example.todo_app.model.Todo;
import com.example.todo_app.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoService todoService;

    @Test
    void add_shouldSaveTodoWithGivenTitle() {
        Todo expectedTodo = new Todo();
        expectedTodo.setTitle("Buy milk");
        expectedTodo.setCompleted(false);
        expectedTodo.setDueDate(java.time.LocalDate.of(2027, 12, 31));
        
        todoService.add("Buy milk", java.time.LocalDate.of(2027, 12, 31));

        verify(todoRepository, times(1)).save(argThat(t ->
            t != null && t.getTitle().equals("Buy milk") && !t.isCompleted() && t.getDueDate().equals(expectedTodo.getDueDate())
        ));
    }

    @Test
    void findAll_shouldReturnAllTodos() {
        Todo t1 = new Todo(); t1.setTitle("A");
        Todo t2 = new Todo(); t2.setTitle("B");
        when(todoRepository.findAll()).thenReturn(List.of(t1, t2));

        List<Todo> result = todoService.findAll();

        assertEquals(2, result.size());
        verify(todoRepository, times(1)).findAll();
    }

    @Test
    void toggleComplete_shouldFlipCompletedStatus() {
        Todo todo = new Todo();
        todo.setTitle("Read book");
        todo.setCompleted(false);
        when(todoRepository.findById(1L)).thenReturn(Optional.of(todo));

        todoService.toggleComplete(1L);

        assertTrue(todo.isCompleted());
        verify(todoRepository, times(1)).save(todo);
    }

    @Test
    void toggleComplete_shouldThrowWhenTodoNotFound() {
        when(todoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
            () -> todoService.toggleComplete(99L));
    }

    @Test
    void delete_shouldCallRepositoryDeleteById() {
        todoService.delete(1L);

        verify(todoRepository, times(1)).deleteById(1L);
    }
}