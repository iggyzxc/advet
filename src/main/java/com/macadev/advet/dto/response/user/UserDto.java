package com.macadev.advet.dto.response.user;

import com.macadev.advet.enums.UserType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String gender;
    private String phoneNumber;
    private String email;
    private UserType userType;
    private boolean isEnabled;
    private LocalDateTime createdAt;

    // role-specific fields have to be removed from here (e.g., specialization for VET)
}
