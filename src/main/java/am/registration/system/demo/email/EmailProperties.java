package am.registration.system.demo.email;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration properties for email settings.
 * <p>
 * The EmailProperties class holds configuration values required for sending emails,
 * such as SMTP server details and authentication settings. These properties are
 * automatically mapped from application properties prefixed with "spring.mail".
 * </p>
 *
 * Author: Artyom Aroyan
 * Date: 21.02.25
 * Time: 22:40:20
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "spring.mail")
public class EmailProperties {
    private String host;
    private int port;
    private String username;
    private String password;
    private boolean smtpAuth;
    private boolean smtpStarttlsEnable;
    private boolean smtpStarttlsRequired;
    private String protocol;
}