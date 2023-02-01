package com.makeitworkch10.pacemakers.pelicare.dto;

import com.makeitworkch10.pacemakers.pelicare.model.CareCircle;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Maaike Feenstra <mk.feenstra@st.hanze.nl><
 * <p>
 * het programma doet
 */

@Getter
@Setter
public class CareCircleDTO {

    private Long careCircleId;
    private String name;

    public CareCircleDTO(CareCircle careCircle){
        name = careCircle.getName();
    }

    public CareCircleDTO(Long id, String name){
    }




}
