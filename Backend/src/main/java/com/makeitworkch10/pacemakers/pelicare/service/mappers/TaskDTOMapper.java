package com.makeitworkch10.pacemakers.pelicare.service.mappers;
import com.makeitworkch10.pacemakers.pelicare.dto.TaskDTO;
import com.makeitworkch10.pacemakers.pelicare.exception.ResourceNotFoundException;
import com.makeitworkch10.pacemakers.pelicare.model.Task;
import com.makeitworkch10.pacemakers.pelicare.repository.CareCircleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;

import java.util.function.Function;

/**
 * @author Maaike Feenstra <mk.feenstra@st.hanze.nl><
 * <p>
 * create mapping for the taskDTO's
 */

@Service

public class TaskDTOMapper implements Function<Task, TaskDTO> {

    @Override
    public TaskDTO apply(Task task) {
        return new TaskDTO(
                task.getId(),
                task.getDate(),
                task.getTitle(),
                task.getDescription(),
                task.isCompletedTask()
        );
    }
}
