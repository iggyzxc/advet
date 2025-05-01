package com.macadev.advet.service.user;

import com.macadev.advet.dto.request.user.UserRegistrationRequestDto;
import com.macadev.advet.dto.request.user.UserUpdateRequestDto;
import com.macadev.advet.dto.response.user.UserDto;

import java.util.List;

public interface IUserService {
    UserDto createUser(UserRegistrationRequestDto request);
    List<UserDto> getAllUsers();
    UserDto getUserById(Long userId);
    UserDto updateUser(Long userId, UserUpdateRequestDto request);
    void deleteUserById(Long userId);
}
