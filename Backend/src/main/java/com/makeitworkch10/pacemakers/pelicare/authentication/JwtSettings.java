package com.makeitworkch10.pacemakers.pelicare.authentication;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Ruben de Vries
 * Configuring JWT settings.
 */

@Component
@ConfigurationProperties(prefix = "jwt.private")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class JwtSettings {
    private String key;
}
