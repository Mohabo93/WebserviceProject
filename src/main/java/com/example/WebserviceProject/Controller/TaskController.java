package com.example.WebserviceProject.Controller;

import com.example.WebserviceProject.Entity.Task;
import com.example.WebserviceProject.Repository.TaskRepository;
import com.example.WebserviceProject.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private UserService userService;

    @GetMapping("")
    public String getAllTasks() {
        List <Task> tasks = userService.getAllTasks();
        String allTask = "Listan på alla task:\n";
        for (Task task : tasks){
            allTask += String.format("ID: %d\nTask: %s\nAssignedTo: %s\n",
                    task.getId(), task.getTask(), task.getAssignedTo());
        }return allTask;
    }

    @GetMapping("/{id}")
    public String getTaskById(@PathVariable Long id) {
        Optional<Task> task = userService.getTaskById(id);
        if (task.isPresent()){
            return String.format("ID: %d\nTask: %s\nAssignedTo: %s\n",
                    task.get().getId(), task.get().getTask(), task.get().getAssignedTo());
        } return "Ingen task med ID " + id + " finns registrerad.";

    }

    @PostMapping("")
    public ResponseEntity <Task> createTask(Authentication authentication, @RequestBody Task task) {
        Task newTask = userService.createTask(task, authentication.getName());
        return ResponseEntity.ok(newTask);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody String updatedTask) {
        Optional<Task> existingTask = userService.getTaskById(id);

        if (existingTask.isPresent()) {
            existingTask.get().setTask(updatedTask);
            Task updated = userService.updateAssignedTo(existingTask.get());

            return new ResponseEntity<>(updated, HttpStatus.OK);
        }else return ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id) {
        Optional <Task> deleteTask = userService.getTaskById(id);
        if (deleteTask.isPresent()){
            userService.deleteTask(id);
            return new ResponseEntity<>("Task raderad", HttpStatus.OK);
        } return new ResponseEntity<>("Task hittades inte", HttpStatus.BAD_REQUEST);
        }
    }
