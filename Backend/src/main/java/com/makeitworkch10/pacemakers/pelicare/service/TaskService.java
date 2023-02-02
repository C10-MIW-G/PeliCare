package com.makeitworkch10.pacemakers.pelicare.service;
import com.makeitworkch10.pacemakers.pelicare.dto.TaskDTO;
import com.makeitworkch10.pacemakers.pelicare.exception.ResourceNotFoundException;
import com.makeitworkch10.pacemakers.pelicare.repository.TaskRepository;
import com.makeitworkch10.pacemakers.pelicare.service.mappers.TaskDTOMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Maaike Feenstra <mk.feenstra@st.hanze.nl><
 * <p>
 * servicelayer for the task entity
 */

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskDTOMapper taskDTOMapper;

    public List<TaskDTO> findAllTasks() {
        return taskRepository.findAll()
                .stream()
                .map(taskDTOMapper)
                .collect(Collectors.toList());
    }

    public TaskDTO getTask(Long id) throws ResourceNotFoundException {
        return taskRepository.findById(id)
                .map(taskDTOMapper)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Task with id not found"
                ));
    }
}
