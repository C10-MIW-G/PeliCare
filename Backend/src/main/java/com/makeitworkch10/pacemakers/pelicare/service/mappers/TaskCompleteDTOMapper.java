package com.makeitworkch10.pacemakers.pelicare.service.mappers;

import com.makeitworkch10.pacemakers.pelicare.dto.TaskCompleteDTO;
import com.makeitworkch10.pacemakers.pelicare.model.Task;

import java.util.function.Function;

/**
 * @author Maaike de Jong
 * Dit is wat het programma doet
 */

public class TaskCompleteDTOMapper implements Function<Task, TaskCompleteDTO> {
    @Override
    public TaskCompleteDTO apply (Task task) {
        return new TaskCompleteDTO(
                task.isCompletedTask(),
                task.getId()
        );
    }
}
