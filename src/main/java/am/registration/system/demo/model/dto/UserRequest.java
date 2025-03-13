package am.registration.system.demo.model.dto;

import am.registration.system.demo.service.user.validation.annotation.ValidEmail;
import am.registration.system.demo.service.user.validation.annotation.ValidPassword;
import am.registration.system.demo.service.user.validation.annotation.ValidPhone;
import am.registration.system.demo.service.user.validation.annotation.ValidUsername;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Author: Artyom Aroyan
 * Date: 13.02.25
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    @ValidUsername
    @Size(min = 5, max = 20)
    private String username;
    @Size(min = 5, max = 20)
    private String fullName;
    @ValidPassword
    @Size(min = 8, max = 30)
    private String password;
    @Email
    @ValidEmail
    private String email;
    @ValidPhone
    @Schema(example = "+37493609556")
    private String phone;
    @Min(value = 18)
    @Max(value = 99)
    private int age;
}