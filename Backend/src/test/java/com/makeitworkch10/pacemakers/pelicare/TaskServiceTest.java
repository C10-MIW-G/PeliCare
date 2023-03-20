package com.makeitworkch10.pacemakers.pelicare;

import com.makeitworkch10.pacemakers.pelicare.dto.TaskDTO;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    public List<TaskDTO> testTaskDTOList1;
    public List<TaskDTO> testTaskDTOList2;
    public List<Task> taskList1;
    public List<Task> taskList2;
    public Task task1, task2, task3;
    public TaskDTO taskDTO1, taskDTO2, taskDTO3;
    public CareCircle circle1;
    public CareCircle circle2;

    @BeforeEach
    public void makeTaskServiceTestInstance(){

        taskService = new TaskService(taskRepository, taskDTOMapper,newTaskDTOMapper);
        testTaskDTOList1 = new ArrayList<>();
        testTaskDTOList2 = new ArrayList<>();
        taskList1 = new ArrayList<>();
        taskList2 = new ArrayList<>();
        circle1 = new CareCircle(1L, "tante Mien", "no image", new ArrayList<>());
        circle2 = new CareCircle(2L, "ome Jaap", "no image", new ArrayList<>());

        task1 = new Task(1L, "water the plants carefully", "plants", false,
                LocalDateTime.of(2023,4,15,11,50),circle1 );

        task2 = new Task(2L, "feed the cat and give tender loving care", "cat", false,
                LocalDateTime.of(2023,4,15,11,50),circle1 );

        task3 = new Task(3L, "trim the hedge", "garden", false,
                LocalDateTime.of(2023,4,15,11,50),circle2 );

        taskList1.add(task1);
        taskList1.add(task2);
        taskList2.add(task3);


        taskDTO1 = new TaskDTO(1L, LocalDateTime.of(2023,4,15,11,50),
                "plants", "water the plants carefully", false );

        taskDTO2 = new TaskDTO(2L, LocalDateTime.of(2023,4,15,12,50),
                "cat", "feed the cat and give tender loving care", false );

        taskDTO3 = new TaskDTO(3L, LocalDateTime.of(2023,4,15,13,50),
                "garden", "trim the hedge", false );

        testTaskDTOList1.add(taskDTO1);
        testTaskDTOList1.add(taskDTO2);
        testTaskDTOList2.add(taskDTO3);
    }

    @Test
    void allTasksOfCareCircle1Test() {

        when(taskRepository.findAllByCareCircleIdOrderByCompletedTaskAscIdDesc(1L))
                .thenReturn(taskList1);

        List<TaskDTO> tasksOfCareCircle1 =  taskService.findAllTasksByCareCircle(1L);
        assertEquals(tasksOfCareCircle1.size(), 2);
    }
}

