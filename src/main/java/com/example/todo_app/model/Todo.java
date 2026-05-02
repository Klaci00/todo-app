package com.example.todo_app.model;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private boolean completed = false;
    private java.time.LocalDate dueDate;

    @CreationTimestamp
    private java.time.LocalDateTime createdAt;
}