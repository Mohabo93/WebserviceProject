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
    private PasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    public List<User>  selectAllUsers() {
        return userRepository.findAll();
    }
    public Optional<User>  selectOneUserById(Long id) {
        return userRepository.findById(id);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Long id, User updatedUser) {
        Optional<User>  existingUser = userRepository.findById(id);

        return existingUser.map(user -> {
            user.setUsername(updatedUser.getUsername());
            user.setPassword(updatedUser.getPassword());

            return userRepository.save(user);
        }).orElse(null);
    }

    public boolean deleteUser(Long id) {
        Optional<User> userOptional  =  userRepository.findById(id);

        if (userOptional.isPresent()) {
            userRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public Task createTask(Task task, String username) {
        Optional <User> user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            task.setUser(user.get());
            return taskRepository.save(task);
        }
        else return null;
        }

        public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public Optional <Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }
    public List<Task> getAll() {
        return taskRepository.findAll();
    }

    public Task updateAssignedTo(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("In the user details service");

        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
