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

    @Autowired
    private UserService userService;


    @PutMapping("/{id}")
    public ResponseEntity<String> updatedUser(@PathVariable Long id, @RequestBody User updatedUser) {
        try {
            User updatedUserResult = userService.updateUser(id, updatedUser);
            if (updatedUserResult != null) {
                return ResponseEntity.ok("Användaren har uppdaterats.");
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Användaren hittades inte");
            }
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        try {
            boolean deleted = userService.deleteUser(id);

            if (deleted) {
                return ResponseEntity.ok(String.format("Användaren med Id har %d har raderats.",  id));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("Användaren med ID %d finns inte.", id));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ERROR: " + e.getMessage());
        }
    }

    @PutMapping("/task/{id}")
    public ResponseEntity <Task> updateTask(@PathVariable Long id, @RequestBody String updatedTask) {
        Optional<Task> existingTask = userService.getTaskById(id);

        if (existingTask.isPresent()) {
            existingTask.get().setTask(updatedTask);
            Task updated = userService.updateAssignedTo(existingTask.get());

            return new ResponseEntity<>(updated, HttpStatus.OK);
        }else return ResponseEntity.notFound().build();
    }
    @DeleteMapping("/task/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id) {
        Optional <Task> deleteTask = userService.getTaskById(id);
        if (deleteTask.isPresent()){
            userService.deleteTask(id);
            return new ResponseEntity<>("Task raderad", HttpStatus.OK);
        } return new ResponseEntity<>("Task hittades inte", HttpStatus.BAD_REQUEST);
    }
}
