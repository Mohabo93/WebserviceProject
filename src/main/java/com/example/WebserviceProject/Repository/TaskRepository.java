package com.example.WebserviceProject.Repository;

import com.example.WebserviceProject.Entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
public interface TaskRepository extends JpaRepository<Task, Long> {
}
