package com.makeitworkch10.pacemakers.pelicare.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Ruben de Vries
 * DTO for creating a new Care Circle.
 */

@Getter
@Setter
@NoArgsConstructor
public class  CreateCareCircleDTO {
    private String name;

    public CreateCareCircleDTO(String name) {
        this.name = name;
    }

}
