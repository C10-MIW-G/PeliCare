package com.makeitworkch10.pacemakers.pelicare.dto;

import com.makeitworkch10.pacemakers.pelicare.model.Task;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Maaike Feenstra <mk.feenstra@st.hanze.nl><
 * <p>
 * het programma doet
 */

@Getter
@Setter
public class TaskDTO {
    private Long id;
    private String description;
    private String title;

    public TaskDTO(Task task){
        title = task.getTitle();
    }


    public TaskDTO(Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }
}
