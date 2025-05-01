package com.macadev.advet.dto.request.user;

import lombok.Data;

@Data
public class UserUpdateRequestDto {

    private String firstName;
    private String lastName;
    private String gender;
    private String phoneNumber;
    private String specialization;
}
