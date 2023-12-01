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

    // Automatiskt injicera en instans av UserService
    @Autowired
    private UserService userService;

    // Hanterar POST-förfrågningar för att skapa en ny User
    @PostMapping
    public String createUser(@RequestBody User user){
        User createdUser = userService.createUser(user);
        return String.format("User %s created with ID %d", createdUser.getUsername(), createdUser.getUserId());
    }
    // Hanterar GET-förfrågningar för att hämta alla Users
    @GetMapping
    public String selectAllUsers(){
        List <User> users = userService.selectAllUsers();
        String resp = "All user names: ";
        for (User user : users){
            resp += user.getUsername() + ", ";
        }
        return resp;
    }
    // Hanterar GET-förfrågningar för att hämta en User baserat på ID
    @GetMapping("/{id}")
    public String selectOneUserById(@PathVariable Long id){
        Optional <User> optionalUser = userService.selectOneUserById(id);
        return optionalUser.map(user -> String.format("User %s have ID %d", user.getUsername(),user.getUserId())).orElse("User not found");
    }
    // Hanterar POST-förfrågningar för att skapa en ny Task för en User
    @PostMapping("/task")
    public ResponseEntity<Task> createTask(Authentication authentication, @RequestBody Task task) {
        Task newTask = userService.createTask(task, authentication.getName());
        return ResponseEntity.ok(newTask);
    }
    // Hanterar GET-förfrågningar för att hämta alla Tasks för en Users
    @GetMapping("/task")
    public String getAllTasks() {
        List <Task> tasks = userService.getAll();
        String allTask = "Tasklist:\n";
        for (Task task : tasks){
            allTask += String.format("ID: %d\nTask: %s\nAssignedTo: %s\n",
                    task.getId(), task.getTask(), task.getAssignedTo());
        }return allTask;
    }

    // Hanterar GET-förfrågningar för att hämta specifik Task baserat på ID
    @GetMapping("/task/{id}")
    public String getTaskById(@PathVariable Long id) {
        Optional<Task> task = userService.getTaskById(id);
        if (task.isPresent()){
            return String.format("ID: %d\nTask: %s\nAssignedTo: %s\n",
                    task.get().getId(), task.get().getTask(), task.get().getAssignedTo());
        } return "No task with ID " + id + " is registered.";
    }
}
