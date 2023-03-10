package com.makeitworkch10.pacemakers.pelicare.controller;

import com.makeitworkch10.pacemakers.pelicare.dto.NewTaskDTO;
import com.makeitworkch10.pacemakers.pelicare.dto.TaskCompleteDTO;
import com.makeitworkch10.pacemakers.pelicare.dto.TaskDTO;
import com.makeitworkch10.pacemakers.pelicare.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Maaike Feenstra <mk.feenstra@st.hanze.nl><
 * <p>
 * Controls taskrelated requests
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/task")
public class TaskController {
    private final TaskService taskService;

    @GetMapping("/all")
    public ResponseEntity<List<TaskDTO>> getAllTasks() {
        List<TaskDTO> responseList = taskService.findAllTasks();
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @GetMapping("/all/{careCircleId}")
    public ResponseEntity<List<TaskDTO>> getAllTasksByCareCircle(@PathVariable("careCircleId") Long careCircleId) {
        List<TaskDTO> responseList = taskService.findAllTasksByCareCircle(careCircleId);
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<TaskDTO> findTask(@PathVariable("id") Long id) {
        TaskDTO task = taskService.getTask(id);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<TaskDTO> newTask(@RequestBody NewTaskDTO newTask) {

        TaskDTO taskDTO = taskService.saveTask(newTask);
        return new ResponseEntity<>(taskDTO,HttpStatus.CREATED);
    }

    @PatchMapping("/patch")
    public ResponseEntity<TaskDTO> patchTask(@RequestBody TaskDTO taskDTO) {
        TaskDTO returnTaskDTO = taskService.patchTask(taskDTO);
        return new ResponseEntity<>(returnTaskDTO, HttpStatus.OK);
    }

    @PatchMapping("/complete")
    public ResponseEntity<TaskCompleteDTO> saveCompleteTask(@RequestBody TaskCompleteDTO taskCompleteDTO) {
        taskService.saveTaskComplete(taskCompleteDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable("id") Long taskId) {
        taskService.deleteTask(taskId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
