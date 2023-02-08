package com.makeitworkch10.pacemakers.pelicare.authentication;

import lombok.Getter;
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
public class JwtSettings {
    private String key;
}
