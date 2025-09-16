package com.example.spring_boot.controller;

import com.example.spring_boot.entity.Task;
import com.example.spring_boot.repo.TaskRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskRepository repository;

    public TaskController(TaskRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Task> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Task getTask(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));
    }

    @PostMapping
    public Task create(@RequestBody Task task) {
        return repository.save(task);
    }

    @PatchMapping("/{id}/completed")
    public Task markAsCompleted(@PathVariable Long id) {
        Task task = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));
        task.setCompleted(true);
        return repository.save(task);
    }

    @DeleteMapping("/{id}")
    public Object delete(@PathVariable Long id) {
        Task task = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));
        repository.delete(task);

        return ResponseEntity.noContent().build();
////        This also works, above is just shortcut:
//        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
