package com.makeitworkch10.pacemakers.pelicare.repository;

import com.makeitworkch10.pacemakers.pelicare.model.CareCircle;
import com.makeitworkch10.pacemakers.pelicare.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CareCircleRepository extends JpaRepository<CareCircle, Long> {
    List<Task> findAllById(Long careCircleId);
}
