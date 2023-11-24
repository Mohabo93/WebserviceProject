package com.example.WebserviceProject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskRepository taskRepository;
    @GetMapping
    public List<TaskEntity> getAllTasks() {
        return taskRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskEntity> getTaskById(@PathVariable Long id) {
        Optional<TaskEntity> task = taskRepository.findById(id);
        return task.map(ResponseEntity::ok).orElseGet(() ->ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TaskEntity> createTask(@RequestBody TaskEntity task) {
        TaskEntity createdTask = taskRepository.save(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);

    }
   @PreAuthorize("hasRole('USER')")
    @PutMapping("/{id}")
    public ResponseEntity<TaskEntity> updateTask(@PathVariable Long id, @RequestBody TaskEntity updatedTask) {
        Optional<TaskEntity> existingTask = taskRepository.findById(id);

        if (existingTask.isPresent()) {
            TaskEntity task = existingTask.get();
            task.setTask(updatedTask.getTask());
            task.setAssignedTo(updatedTask.getAssignedTo());
            taskRepository.save(task);
            return ResponseEntity.ok(task);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskRepository.deleteById(id);
        return ResponseEntity.noContent().build();
        }
    }
