package com.example.WebserviceProject.Controller;


import com.example.WebserviceProject.Entity.Task;
import com.example.WebserviceProject.Entity.User;
import com.example.WebserviceProject.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
public class AdminController {

    // Automatiskt injicera en instans av UserService
    @Autowired
    private UserService userService;

    // Hanterar PUT-förfrågningar för att uppdatera användaren baserat på ID
    @PutMapping("/{id}")
    public ResponseEntity<String> updatedUser(@PathVariable Long id, @RequestBody User updatedUser) {
        try {
            User updatedUserResult = userService.updateUser(id, updatedUser);
            if (updatedUserResult != null) {
                return ResponseEntity.ok("User has been updated.");
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    // Hanterar DELETE-förfrågningar för att ta bort användaren baserat på ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        try {
            boolean deleted = userService.deleteUser(id);

            if (deleted) {
                return ResponseEntity.ok(String.format("User with ID %d has been deleted.",  id));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("User with ID %d not found.", id));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ERROR: " + e.getMessage());
        }
    }

    // Hanterar PUT-förfrågningar för att uppdatera Task baserat på ID
    @PutMapping("/task/{id}")
    public ResponseEntity <Task> updateTask(@PathVariable Long id, @RequestBody String updatedTask) {
        Optional<Task> existingTask = userService.getTaskById(id);

        if (existingTask.isPresent()) {
            existingTask.get().setTask(updatedTask);
            Task updated = userService.updateAssignedTo(existingTask.get());

            return new ResponseEntity<>(updated, HttpStatus.OK);
        }else return ResponseEntity.notFound().build();
    }
    // Hanterar DELETE-förfrågningar för att ta bort Task baserat på ID
    @DeleteMapping("/task/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id) {
        Optional <Task> deleteTask = userService.getTaskById(id);
        if (deleteTask.isPresent()){
            userService.deleteTask(id);
            return new ResponseEntity<>("Task deleted", HttpStatus.OK);
        } return new ResponseEntity<>("Task not found", HttpStatus.BAD_REQUEST);
    }
}
