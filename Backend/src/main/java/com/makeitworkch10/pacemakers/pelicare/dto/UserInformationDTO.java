package com.makeitworkch10.pacemakers.pelicare.dto;

import lombok.*;

/**
 * @author Maaike de Jong
 * DTO to only send user information to the frondend
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInformationDTO {
    private String email;
    private String name;
    private String phoneNumber;
}
