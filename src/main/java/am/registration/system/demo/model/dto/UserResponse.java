package am.registration.system.demo.model.dto;

import am.registration.system.demo.model.enums.UserState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * Author: Artyom Aroyan
 * Date: 13.02.25
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private Integer id;
    private Date createdDate;
    private Date updatedDate;
    private String username;
    private String fullName;
    private String email;
    private String phone;
    private int age;
    private UserState userState;
}