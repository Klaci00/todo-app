package com.example.todo_app.controller;

import com.example.todo_app.model.Todo;
import com.example.todo_app.service.TodoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TodoController.class)
class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TodoService todoService;

    @Test
    void index_shouldReturnIndexViewWithTodosInTable() throws Exception {
        Todo todo = new Todo();
        todo.setTitle("Test task");
        when(todoService.findAll()).thenReturn(List.of(todo));

        mockMvc.perform(get("/"))
            .andExpect(status().isOk())
            .andExpect(view().name("index"))
            .andExpect(model().attributeExists("todos"))
            .andExpect(model().attribute("todos", List.of(todo)))
            .andExpect(content().string(org.hamcrest.Matchers.containsString("<td>")))
            .andExpect(content().string(org.hamcrest.Matchers.containsString("Test task")))
            .andExpect(content().string(org.hamcrest.Matchers.containsString("<form")))
            .andExpect(content().string(org.hamcrest.Matchers.containsString("Due Date")));
    }

    @Test
    void add_shouldCallServiceAndRedirect() throws Exception {
        mockMvc.perform(post("/add").param("title", "New task").param("dueDate", java.time.LocalDate.of(2027, 12, 31).toString()))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/"));

        verify(todoService, times(1)).add("New task", java.time.LocalDate.of(2027, 12, 31));
    }

    @Test
    void toggle_shouldCallServiceAndRedirect() throws Exception {
        mockMvc.perform(post("/toggle/1"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/"));

        verify(todoService, times(1)).toggleComplete(1L);
    }

    @Test
    void delete_shouldCallServiceAndRedirect() throws Exception {
        mockMvc.perform(post("/delete/1"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/"));

        verify(todoService, times(1)).delete(1L);
    }
}