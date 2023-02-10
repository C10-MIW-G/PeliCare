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

    @GetMapping("/{id}/complete")
    public ResponseEntity<TaskCompleteDTO> findCompleteTask(@PathVariable("id") Long id) {
        TaskCompleteDTO taskCompleteDTO = taskService.getTaskComplete(id);
        return new ResponseEntity<>(taskCompleteDTO, HttpStatus.OK);
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<TaskCompleteDTO> saveCompleteTask(
            @PathVariable("id") Long id, @RequestBody TaskCompleteDTO taskCompleteDTO) {
        taskService.saveTaskComplete(id, taskCompleteDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
