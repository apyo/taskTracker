package com.example.spring_boot.repo;

import com.example.spring_boot.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByCompleted(boolean completed);

    List<Task> findByCreatedAtBefore(Instant time);

    List<Task> findByCreatedAtBeforeAndCompleted(Instant time, boolean completed);
}
