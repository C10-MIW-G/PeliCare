package com.makeitworkch10.pacemakers.pelicare.service;
import com.makeitworkch10.pacemakers.pelicare.dto.NewTaskDTO;
import com.makeitworkch10.pacemakers.pelicare.dto.TaskCompleteDTO;
import com.makeitworkch10.pacemakers.pelicare.dto.TaskDTO;
import com.makeitworkch10.pacemakers.pelicare.exception.ResourceNotFoundException;
import com.makeitworkch10.pacemakers.pelicare.model.Task;
import com.makeitworkch10.pacemakers.pelicare.repository.TaskRepository;
import com.makeitworkch10.pacemakers.pelicare.service.mappers.NewTaskDTOMapper;
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
    private final NewTaskDTOMapper newTaskDTOMapper;

    public List<TaskDTO> findAllTasks() {
        return taskRepository.findAll()
                .stream()
                .map(taskDTOMapper)
                .collect(Collectors.toList());
    }

    public List<TaskDTO> findAllTasksByCareCircle(Long careCircleId){
        return taskRepository.findAllByCareCircleIdOrderByCompletedTaskAscIdDesc(careCircleId)
                .stream()
                .map(taskDTOMapper)
                .collect(Collectors.toList());
    }

    public TaskDTO getTask(Long id) throws ResourceNotFoundException {
        return taskRepository.findById(id)
                .map(taskDTOMapper)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Task with id " + id + " not found"
                ));
    }

    public TaskDTO saveTask(NewTaskDTO newTaskDTO) {
        Task task = newTaskDTOMapper.apply(newTaskDTO);
        Long taskId = taskRepository.save(task).getId();
        return taskRepository.findById(taskId)
                .map(taskDTOMapper)
                .orElseThrow(() -> new ResourceNotFoundException("Task with id " + taskId + " not found"));
    }

    public TaskDTO patchTask(TaskDTO taskDTO) {
        Task taskToUpdate = taskRepository.findById(taskDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                "this Task could not be found"));
        taskToUpdate.setDate(taskDTO.getDate());
        taskToUpdate.setTitle(taskDTO.getTitle());
        taskToUpdate.setDescription(taskDTO.getDescription());
        taskRepository.save(taskToUpdate);
        return new TaskDTO(taskToUpdate.getId(),
                                taskToUpdate.getDate(),
                                taskToUpdate.getTitle(),
                                taskToUpdate.getDescription(),
                                taskToUpdate.isCompletedTask());
    }

    public void saveTaskComplete(TaskCompleteDTO taskCompleteDTO) {
        TaskDTO taskdto = getTask(taskCompleteDTO.getId());
        taskdto.setCompletedTask(taskCompleteDTO.isCompletedTask());
        Task task = taskRepository.findById(taskCompleteDTO.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Task with id " + taskCompleteDTO.getId() + " not found"));
        task.setCompletedTask(taskdto.isCompletedTask());
        taskRepository.save(task);
    }

    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }
}
