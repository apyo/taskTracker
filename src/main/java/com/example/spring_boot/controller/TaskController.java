package com.example.spring_boot.controller;

import com.example.spring_boot.entity.Task;
import com.example.spring_boot.repo.TaskRepository;
import dto.TaskCreateRequest;
import dto.TaskUpdateRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
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
    public Task create(@RequestBody TaskCreateRequest request) {
        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setCompleted(false);
        return repository.save(task);
    }

    @PatchMapping("/{id}")
    public Task update(@PathVariable Long id, @RequestBody TaskUpdateRequest updates) {
        Task originalTask = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));
        if (updates.getTitle() != null) {
            originalTask.setTitle(updates.getTitle());
        }
        if (updates.getDescription() != null) {
            originalTask.setDescription(updates.getDescription());
        }
        if (updates.getCompleted() != null) {
            originalTask.setCompleted(updates.getCompleted());
        }
        return repository.save(originalTask);
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
