package com.makeitworkch10.pacemakers.pelicare.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author Paul Moonen
 * <p>
 * p.c.c.moonen@gmail.com
 * <p>
 * DTO used to hold user provided data, to make a new Task.
 * We cannot use a normal TaskDTO for this purpose:
 * 1)   The Task id only exists after saving in the database, and
 * 2)   the CareCircle id that is needed to make the Task is included.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewTaskDTO {
    private String description;
    private String title;
    private Long careCircleId;
    private LocalDateTime date;
}
