package com.makeitworkch10.pacemakers.pelicare.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Maaike de Jong
 * Passes to the front end whether a given task is completed or not
 */
@Getter
@Setter
public class TaskCompleteDTO {
    private boolean completedTask;
    private Long id;

    public TaskCompleteDTO(boolean completedTask, Long id) {
        this.completedTask = completedTask;
        this.id = id;
    }
}
