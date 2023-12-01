package com.example.WebserviceProject.Service;

import com.example.WebserviceProject.Entity.Task;
import com.example.WebserviceProject.Entity.User;
import com.example.WebserviceProject.Repository.TaskRepository;
import com.example.WebserviceProject.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private PasswordEncoder encoder; // Injicering av PasswordEncoder för att koda och dekoda lösenord

    @Autowired
    private UserRepository userRepository; // Injicering av UserRepository för att utföra operationer mot användaruppgifter

    @Autowired
    private TaskRepository taskRepository; // Injicering av TaskRepository för att utföra operationer mot uppgifter

    // Hämtar alla användare från databasen
    public List<User>  selectAllUsers() {
        return userRepository.findAll();
    }
    // Hämtar en användare från databasen baserat på ID
    public Optional<User>  selectOneUserById(Long id) {
        return userRepository.findById(id);
    }

    // Skapar en ny användare i databasen
    public User createUser(User user) {
        return userRepository.save(user);
    }

    // Uppdatera en befintlig användare baserat på ID
    public User updateUser(Long id, User updatedUser) {
        Optional<User>  existingUser = userRepository.findById(id);

        return existingUser.map(user -> {
            user.setUsername(updatedUser.getUsername());
            user.setPassword(updatedUser.getPassword());

            return userRepository.save(user);
        }).orElse(null);
    }

    // Tar bort en användare baserat på ID
    public boolean deleteUser(Long id) {
        Optional<User> userOptional  =  userRepository.findById(id);

        if (userOptional.isPresent()) {
            userRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    // Skapar en ny Task för användare baserat på användarnamn
    public Task createTask(Task task, String username) {
        Optional <User> user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            task.setUser(user.get());
            return taskRepository.save(task);
        }
        else return null;
        }

        // Tar bort en Task baserat på ID
        public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    // Hämtar en Task baserat på ID
    public Optional <Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    // Hämtar alla Tasks från databasen
    public List<Task> getAll() {
        return taskRepository.findAll();
    }

    //Uppdaterar task-tilldelning
    public Task updateAssignedTo(Task task) {
        return taskRepository.save(task);
    }

    // Metod från UserDetailsService-gränssnittet som används för att ladda användaruppgifter vid autentisering
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("In the user details service");

        // Hämtar Användaruppgifter från databasen baserat på användarnamn
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
