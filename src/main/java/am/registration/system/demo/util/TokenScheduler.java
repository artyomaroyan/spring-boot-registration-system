package am.registration.system.demo.util;

import am.registration.system.demo.model.repository.UserTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * A scheduled task responsible for managing user tokens by marking expired tokens in the database.
 * This task runs periodically to ensure that expired tokens are flagged appropriately.
 *  *
 *  * Annotations:
 * - @Slf4j: Provides a logger instance (from Lombok) to facilitate logging within the class.
 * - @Component: Marks this class as a Spring bean, allowing it to be detected during component scanning.
 * - @EnableScheduling: Enables support for scheduled tasks.
 * - @RequiredArgsConstructor: Generates a constructor with required final fields (using Lombok).
 * - @Transactional: Ensures that the scheduled method runs within a transactional context.
 * - @Scheduled: Configures the scheduling interval for executing the task.
 *  *
 *  * Schedule Configuration:
 * - Runs every 15 minutes (fixed rate of 15 * 60 * 1000 milliseconds).
 * - The task execution time is not influenced by the duration of the previous execution.
 * *
 * Author: Artyom Aroyan
 * Date: 24.02.25
 * Time: 22:40:44
 */
@Slf4j
@Component
@EnableScheduling
@RequiredArgsConstructor
public class TokenScheduler {

    private final UserTokenRepository userTokenRepository;

    /**
     * Scheduled method to mark expired user tokens in the database.
     * Runs every 15 minutes to ensure token expiration status is up to date.
     * Use a transactional context to guarantee consistency during database operations.
     */
    @Transactional
    @Scheduled(fixedRate = 15 * 60 * 1000)
    public void schedule() {
        log.info(LogMessages.START_SCHEDULE);
        int expiredTokens = userTokenRepository.markExpiredTokens();
        log.info(LogMessages.FINISH_SCHEDULE, expiredTokens);
    }
}