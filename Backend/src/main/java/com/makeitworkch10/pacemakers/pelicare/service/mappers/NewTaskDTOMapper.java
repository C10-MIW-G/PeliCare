package com.makeitworkch10.pacemakers.pelicare.service.mappers;

import com.makeitworkch10.pacemakers.pelicare.dto.NewTaskDTO;
import com.makeitworkch10.pacemakers.pelicare.exception.ResourceNotFoundException;
import com.makeitworkch10.pacemakers.pelicare.model.Task;
import com.makeitworkch10.pacemakers.pelicare.repository.CareCircleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

/**
 * @author Maaike Feenstra <mk.feenstra@st.hanze.nl><
 * <p>
 * create mapping for the newTaskDTO's
 */

@Service
@RequiredArgsConstructor
public class NewTaskDTOMapper implements Function<NewTaskDTO, Task> {
    private final CareCircleRepository careCircleRepository;

    @Override
    public Task apply(NewTaskDTO newTaskDTO) {
        Task task = new Task();
        task.setDate(newTaskDTO.getDate());
        task.setTitle(newTaskDTO.getTitle());
        task.setDescription(newTaskDTO.getDescription());
        task.setCareCircle(careCircleRepository.findById(newTaskDTO.getCareCircleId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Care Circle of this new task could not be found")));

        return task;
    }
}
