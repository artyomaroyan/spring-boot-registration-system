package am.registration.system.demo.email;

import am.registration.system.demo.util.LogMessages;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Service responsible for sending emails asynchronously.
 * <p>
 * The EmailSender class provides methods to send simple text-based emails using
 * the configured SMTP server. It leverages asynchronous execution to improve performance
 * and avoid blocking the main application thread.
 * </p>
 *
 * Author: Artyom Aroyan
 * Date: 21.02.25
 * Time: 23:31:12
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EmailSender {

    private final JavaMailSender mailSender;
    private final EmailProperties emailProperties;

    /**
     * Sends an email asynchronously.
     * <p>
     * This method creates a simple mail message, sets the necessary fields (recipient,
     * subject, content), and sends the email using the configured JavaMailSender instance.
     * The asynchronous annotation ensures the method is executed in a separate thread.
     * </p>
     *
     * @param to      the recipient's email address
     * @param subject the subject of the email
     * @param content the body content of the email
     */
    @Async
    public void send(String to, String subject, String content) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSentDate(new Date());
            message.setFrom(emailProperties.getUsername());
            message.setTo(to);
            message.setSubject(subject);
            message.setText(content);

            mailSender.send(message);
            log.info(LogMessages.EMAIL_SUCCESSFULLY_SENT, to);
        } catch (Exception ex) {
            log.error(LogMessages.FAILED_TO_SEND_EMAIL, to, ex.getMessage());
        }
    }
}