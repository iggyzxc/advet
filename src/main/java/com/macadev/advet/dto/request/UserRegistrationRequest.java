package com.macadev.advet.dto.request;

import com.macadev.advet.model.UserType;
import lombok.Data;

@Data
public class UserRegistrationRequest {

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
