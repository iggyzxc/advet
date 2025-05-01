package com.macadev.advet.dto.request.user;

import com.macadev.advet.enums.UserType;
import lombok.Data;

@Data
public class UserRegistrationRequestDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String gender;
    private String phoneNumber;
    private String email;
    private String password;
    private UserType userType;
    private boolean isEnabled;
    private String specialization;
}
