package com.makeitworkch10.pacemakers.pelicare.dto;

import com.makeitworkch10.pacemakers.pelicare.model.CareCircle;
import com.makeitworkch10.pacemakers.pelicare.model.Task;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author Maaike Feenstra <mk.feenstra@st.hanze.nl><
 * <p>
 * het programma doet
 */

@Getter
@Setter
public class CareCircleDTO {
    private Long id;
    private String name;


    public CareCircleDTO(CareCircle careCircle){
        name = careCircle.getName();
    }


    public CareCircleDTO(String name, Long id) {
        this.name = name;
        this.id = id;
    }

    private List<TaskDTO> taskList;
}
