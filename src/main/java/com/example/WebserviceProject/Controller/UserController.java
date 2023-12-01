package com.example.WebserviceProject.Controller;


import com.example.WebserviceProject.Entity.Task;
import com.example.WebserviceProject.Entity.User;
import com.example.WebserviceProject.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String selectAllUsers(){
        List <User> users = userService.selectAllUsers();
        String resp = "Alla namn på users: ";
        for (User user : users){
            resp += user.getUsername() + ", ";
        }
        return resp;
    }

    @GetMapping("/{id}")
    public String selectOneUserById(@PathVariable Long id){
        Optional <User> optionalUser = userService.selectOneUserById(id);
        return optionalUser.map(user -> String.format("Användaren %s har ID %d", user.getUsername(),user.getUserId())).orElse("Användaren hittas inte");
    }

    @PostMapping
    public String createUser(@RequestBody User user){
        User createdUser = userService.createUser(user);
        return String.format("Användaren %s skapad med ID %d", createdUser.getUsername(), createdUser.getUserId());
    }

    @GetMapping("/task")
    public String getAllTasks() {
        List <Task> tasks = userService.getAll();
        String allTask = "Listan på alla task:\n";
        for (Task task : tasks){
            allTask += String.format("ID: %d\nTask: %s\nAssignedTo: %s\n",
                    task.getId(), task.getTask(), task.getAssignedTo());
        }return allTask;
    }

    @GetMapping("/task/{id}")
    public String getTaskById(@PathVariable Long id) {
        Optional<Task> task = userService.getTaskById(id);
        if (task.isPresent()){
            return String.format("ID: %d\nTask: %s\nAssignedTo: %s\n",
                    task.get().getId(), task.get().getTask(), task.get().getAssignedTo());
        } return "Ingen task med ID " + id + " finns registrerad.";

    }

    @PostMapping("/task")
    public ResponseEntity<Task> createTask(Authentication authentication, @RequestBody Task task) {
        Task newTask = userService.createTask(task, authentication.getName());
        return ResponseEntity.ok(newTask);
    }
}
