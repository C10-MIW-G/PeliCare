package com.makeitworkch10.pacemakers.pelicare.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Maaike Feenstra <mk.feenstra@st.hanze.nl><
 * <p>
 * Sends only the requested information of the Task
 */

@Getter
@Setter
public class TaskDTO {
    private Long id;
    private String description;
    private String title;


    public TaskDTO(Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }
}
