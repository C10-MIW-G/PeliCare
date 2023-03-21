package com.makeitworkch10.pacemakers.pelicare.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Paul Moonen
 * <p>
 * p.c.c.moonen@gmail.com
 * <p>
 * list all users of a Care Circle, including their admin status in this Care circle
 */
@Getter
@Setter
public class CareCircleUserDTO {

    private Long circleId;
    private Long userId;
    private Boolean isUser;
    private Boolean isAdmin;
    private String email;
    private String name;
    private String phoneNumber;
}
