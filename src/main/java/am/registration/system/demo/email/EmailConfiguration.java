package am.registration.system.demo.email;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * Configuration class for setting up the JavaMailSender bean.
 * <p>
 * The EmailConfiguration class configures the email sending functionality
 * using properties from the EmailProperties class. It sets the SMTP server
 * details, authentication, and protocol configurations to enable secure email
 * transmission.
 * </p>
 *
 * Author: Artyom Aroyan
 * Date: 21.02.25
 * Time: 23:25:33
 */
@Configuration
@RequiredArgsConstructor
public class EmailConfiguration {

    private final EmailProperties emailProperties;

    /**
     * Configures and provides a JavaMailSender bean.
     * <p>
     * Uses SMTP settings from EmailProperties to configure the JavaMailSenderImpl.
     * Enables STARTTLS and SMTP authentication to ensure secure email communication.
     * </p>
     *
     * @return a configured JavaMailSender bean
     */
    @Bean
    protected JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(emailProperties.getHost());
        mailSender.setPort(emailProperties.getPort());
        mailSender.setUsername(emailProperties.getUsername());
        mailSender.setPassword(emailProperties.getPassword());

        Properties properties = mailSender.getJavaMailProperties();
        properties.put("mail.smtp.auth", emailProperties.isSmtpAuth());
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.starttls.required", emailProperties.isSmtpStarttlsRequired());
        properties.put("mail.transport.protocol", emailProperties.getProtocol());

        mailSender.setJavaMailProperties(properties);
        return mailSender;
    }
}