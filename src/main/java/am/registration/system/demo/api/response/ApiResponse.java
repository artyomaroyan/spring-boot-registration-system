package am.registration.system.demo.api.response;

import java.time.LocalDateTime;

/**
 * Author: Artyom Aroyan
 * Date: 22.02.25
 * Time: 01:12:28
 */
public class ApiResponse {

  public static <T> ApiResponseBuilder<T> success(T data, String message) {
      return ApiResponseBuilder.<T>builder()
              .data(data)
              .message(message == null || message.isEmpty() ? "" : message)
              .success(true)
              .timestamp(LocalDateTime.now())
              .build();
  }

    public static <T> ApiResponseBuilder<T> success(String message) {
        return success(null, message);
    }

    public static <T> ApiResponseBuilder<T> failure(String message, String errorCode) {
        return ApiResponseBuilder.<T>builder()
                .data(null)
                .message(message)
                .success(false)
                .errorCode(errorCode)
                .timestamp(LocalDateTime.now())
                .build();
    }
}