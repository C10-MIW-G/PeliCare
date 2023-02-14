package com.makeitworkch10.pacemakers.pelicare.dto;

import com.makeitworkch10.pacemakers.pelicare.model.Task;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author Maaike Feenstra <mk.feenstra@st.hanze.nl><
 * <p>
 * Sends only the requested information of the CareCircle
 */

@Getter
@Setter
public class CareCircleDTO {
    private Long id;
    private String name;

    private List<Task> taskList;

    public CareCircleDTO(Long id, String name, List<Task> taskList) {
        this.name = name;
        this.taskList = taskList;
        this.id = id;
    }
}
