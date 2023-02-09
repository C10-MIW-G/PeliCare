package com.makeitworkch10.pacemakers.pelicare.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Paul Moonen
 * <p>
 * p.c.c.moonen@gmail.com
 * <p>
 * beschrijving van het programma
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewTaskDTO {
    private String description;
    private String title;
    private Long careCircleId;
}
