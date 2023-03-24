package com.makeitworkch10.pacemakers.pelicare;

import com.makeitworkch10.pacemakers.pelicare.dto.TaskCompleteDTO;
import com.makeitworkch10.pacemakers.pelicare.dto.TaskDTO;
import com.makeitworkch10.pacemakers.pelicare.exception.ResourceNotFoundException;
import com.makeitworkch10.pacemakers.pelicare.model.CareCircle;
import com.makeitworkch10.pacemakers.pelicare.model.Task;
import com.makeitworkch10.pacemakers.pelicare.repository.TaskRepository;
import com.makeitworkch10.pacemakers.pelicare.service.TaskService;
import com.makeitworkch10.pacemakers.pelicare.service.mappers.NewTaskDTOMapper;
import com.makeitworkch10.pacemakers.pelicare.service.mappers.TaskDTOMapper;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
@RequiredArgsConstructor
/**
 * @author Paul Moonen
 * <p>
 * p.c.c.moonen@gmail.com
 * <p>
 * test class for TaskService
 */

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private TaskRepository taskRepository;
    @Mock
    private TaskDTOMapper taskDTOMapper;
    @Mock
    private NewTaskDTOMapper newTaskDTOMapper;
    @InjectMocks
    private TaskService taskService;
    public List<Task> taskList1;
    public Task task1, task2, task3;
    public CareCircle circle1;
    public CareCircle circle2;

    /**
     * prepare test data and simulate the TaskRepository and two DTO-mappers
     */
    @BeforeEach
    public void makeTaskServiceTestInstance(){

        taskService = new TaskService(taskRepository, taskDTOMapper,newTaskDTOMapper);

        circle1 = new CareCircle(1L, "tante Mien", "no image", new ArrayList<>());

        task1 = new Task(1L, "water the plants carefully", "plants", false,
                LocalDateTime.of(2023,4,15,11,50),circle1 );

        task2 = new Task(2L, "feed the cat and give tender loving care", "cat", false,
                LocalDateTime.of(2023,4,15,11,50),circle1 );

        task3 = new Task(3L, "trim the hedge", "garden", false,
                LocalDateTime.of(2023,4,15,11,50),circle2 );

        taskList1 = new ArrayList<>();
        taskList1.add(task1);
        taskList1.add(task2);

    }

    @Test
    void allTasksOfCareCircle1Test() {

        when(taskRepository.findAllByCareCircleIdOrderByCompletedTaskAscIdDesc(1L))
                .thenReturn(taskList1);

        List<TaskDTO> tasksOfCareCircle1 =  taskService.findAllTasksByCareCircle(1L);
        assertEquals(tasksOfCareCircle1.size(), 2);
    }

    @Test
    void getTaskByIdTest() {

        when(taskRepository.findById(3L)).thenReturn(Optional.ofNullable(task3));
        TaskDTO testTaskDTO = new TaskDTO();
        testTaskDTO.setId(3L);
        when(taskDTOMapper.apply(task3)).thenReturn(testTaskDTO);

        assertEquals( 3L, taskService.getTask(3L).getId());
    }

    @Test
    void cannotFindNonExistingTaskTest() {

        when(taskRepository.findById(25L)).thenReturn(Optional.ofNullable(null));
        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(()-> taskService.getTask(25L))
                .withMessage( "Task with id 25 not found");
    }

    @Test
    void saveTaskCompleteTest() {
        TaskCompleteDTO taskCompleteDTO = new TaskCompleteDTO(true, 3L);

        when(taskRepository.findById(taskCompleteDTO.getId()))
                .thenReturn(Optional.ofNullable(task3));
        when(taskRepository.save(task3)).thenReturn(task3);
        when(taskRepository.findById(3L)).thenReturn(Optional.ofNullable(task3));
        TaskDTO testTaskDTO = new TaskDTO();
        testTaskDTO.setId(3L);
        when(taskDTOMapper.apply(task3)).thenReturn(testTaskDTO);
        taskService.saveTaskComplete(taskCompleteDTO);
        assertThat(task3.isCompletedTask()).isTrue();
    }

    @Test
    void saveTaskThrowExceptionTest()throws ResourceNotFoundException {
        TaskCompleteDTO taskCompleteDTO = new TaskCompleteDTO(true, 4L);

        assertThrows(ResourceNotFoundException.class,
                ()->taskService.getTask(taskCompleteDTO.getId()));
    }
}

