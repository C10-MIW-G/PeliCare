package com.makeitworkch10.pacemakers.pelicare.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Paul Moonen
 * <p>
 * p.c.c.moonen@gmail.com
 * <p>
 * receive data from front end to toggle admin status of a Care Circle member
 */
@Getter
@Setter
public class ToggleAdminStatusDTO {
    private String email;
    private Long circleId;
}
