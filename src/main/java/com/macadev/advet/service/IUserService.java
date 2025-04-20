package com.macadev.advet.service;

import com.macadev.advet.dto.request.UserRegistrationRequest;
import com.macadev.advet.dto.request.UserUpdateRequest;
import com.macadev.advet.dto.response.UserDto;
import com.macadev.advet.model.User;

import java.util.List;

public interface IUserService {
    UserDto register(UserRegistrationRequest request);

    UserDto update(Long userId, UserUpdateRequest request);

    UserDto getUserById(Long userId);

    void delete(Long userId);

    List<UserDto> getAllUsers();
}
