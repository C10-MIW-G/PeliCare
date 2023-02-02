package com.makeitworkch10.pacemakers.pelicare.service.mappers;
import com.makeitworkch10.pacemakers.pelicare.dto.TaskDTO;
import com.makeitworkch10.pacemakers.pelicare.model.Task;
import org.springframework.stereotype.Service;

import java.util.function.Function;

/**
 * @author Maaike Feenstra <mk.feenstra@st.hanze.nl><
 * <p>
 * het programma doet
 */

@Service
public class TaskDTOMapper implements Function<Task, TaskDTO> {
    @Override
    public TaskDTO apply(Task task) {
        return new TaskDTO(
                task.getTitle(),
                task.getDescription()
        );
    }

}
