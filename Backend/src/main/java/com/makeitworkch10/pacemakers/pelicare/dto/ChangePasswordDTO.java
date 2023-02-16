package com.makeitworkch10.pacemakers.pelicare.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author: Ramon de Wilde <r.de.wilde@st.hanze.nl>
 * <p>
 * Sends only the requested data for the change password
 */

@Getter
@Setter
@NoArgsConstructor
public class ChangePasswordDTO {
    private String oldPassword;
    private String newPassword;
}
