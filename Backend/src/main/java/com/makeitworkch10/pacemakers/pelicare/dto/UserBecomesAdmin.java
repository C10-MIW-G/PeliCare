package com.makeitworkch10.pacemakers.pelicare.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Paul Moonen
 * <p>
 * p.c.c.moonen@gmail.com
 * <p>
 * receive data from front end to promote a Care Circle User to Admin of the same Care Circle
 */
@Getter
@Setter
public class UserBecomesAdmin {
    private String email;
    private Long circleId;
}
