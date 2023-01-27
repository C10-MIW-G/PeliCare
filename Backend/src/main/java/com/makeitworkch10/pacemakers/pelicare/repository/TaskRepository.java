package com.makeitworkch10.pacemakers.pelicare.repository;

import com.makeitworkch10.pacemakers.pelicare.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
