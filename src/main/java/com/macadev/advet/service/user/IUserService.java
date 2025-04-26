package com.macadev.advet.service.user;

import com.macadev.advet.dto.request.UserRegistrationRequest;
import com.macadev.advet.dto.request.UserUpdateRequest;
import com.macadev.advet.dto.response.user.UserDto;

import java.util.List;

public interface IUserService {
    UserDto registerUser(UserRegistrationRequest request);
    UserDto updateUser(Long userId, UserUpdateRequest request);
    List<UserDto> getAllUsers();
    UserDto getUserById(Long userId);
    void deleteUserById(Long userId);
}
