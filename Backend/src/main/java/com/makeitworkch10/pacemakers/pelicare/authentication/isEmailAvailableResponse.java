package com.makeitworkch10.pacemakers.pelicare.authentication;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Ruben de Vries
 * Response to the front-end validating if the requested email is still available.
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class isEmailAvailableResponse {
    private boolean isAvailable;
}
