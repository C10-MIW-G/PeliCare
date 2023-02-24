package com.makeitworkch10.pacemakers.pelicare.repository;

import com.makeitworkch10.pacemakers.pelicare.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllByCareCircleIdOrderByCompletedTaskAscIdDesc(Long careCircleId);

}
