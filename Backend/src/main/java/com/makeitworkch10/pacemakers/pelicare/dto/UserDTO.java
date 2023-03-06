package com.makeitworkch10.pacemakers.pelicare.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Maaike de Jong
 * DTO to pass useremail and ID along
 */
@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String email;

    public UserDTO(Long id, String email) {
        this.id = id;
        this.email = email;
    }
}
