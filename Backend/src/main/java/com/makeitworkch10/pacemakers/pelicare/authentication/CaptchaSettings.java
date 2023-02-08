package com.makeitworkch10.pacemakers.pelicare.authentication;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Ruben de Vries
 * Configuring recaptcha settings
 */

@Component
@ConfigurationProperties(prefix = "google.recaptcha.key")
@Getter
@Setter
public class CaptchaSettings {
    private String secret;
}
