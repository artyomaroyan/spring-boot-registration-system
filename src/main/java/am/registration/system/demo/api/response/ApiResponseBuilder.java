package am.registration.system.demo.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 * Author: Artyom Aroyan
 * Date: 22.02.25
 * Time: 01:08:36
 */
@Builder
@JsonInclude(NON_NULL)
public record ApiResponseBuilder<T>(T data, String message, boolean success, String errorCode, LocalDateTime timestamp) {
}