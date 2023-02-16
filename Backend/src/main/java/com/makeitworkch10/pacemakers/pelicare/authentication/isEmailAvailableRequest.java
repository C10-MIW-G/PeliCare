package com.makeitworkch10.pacemakers.pelicare.authentication;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Ruben de Vries
 * Request from the front-end for validating if the email is available for registering.
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class isEmailAvailableRequest {
    private String userEmail;
}
