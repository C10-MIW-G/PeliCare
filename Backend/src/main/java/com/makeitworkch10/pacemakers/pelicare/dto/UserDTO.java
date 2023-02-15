package com.makeitworkch10.pacemakers.pelicare.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * @author Maaike de Jong
 * Dit is wat het programma doet
 */
@Getter
@Setter
public class UserDTO {
    private Long id;
    private String email;

    public UserDTO(Long id, String email) {
        this.id = id;
        this.email = email;
    }
}
