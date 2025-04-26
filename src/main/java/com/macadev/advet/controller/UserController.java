package com.macadev.advet.controller;

import com.macadev.advet.dto.request.UserUpdateRequest;
import com.macadev.advet.dto.response.user.UserDto;
import com.macadev.advet.dto.request.UserRegistrationRequest;
import com.macadev.advet.dto.response.ApiResponse;

import com.macadev.advet.enums.ResourceType;
import com.macadev.advet.service.user.UserService;
import com.macadev.advet.util.FeedbackMessage;
import com.macadev.advet.util.UrlMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(UrlMapping.USERS_BASE)
public class UserController {
    private final UserService userService; // Only depends on the service

    // CREATE User: POST /api/v1/users
    @PostMapping
    public ResponseEntity<ApiResponse<UserDto>> registerUser(@RequestBody UserRegistrationRequest request) {
            // No try-catch needed here!
            UserDto userDto = userService.registerUser(request);
            ApiResponse<UserDto> response = new ApiResponse<>(FeedbackMessage.createSuccess(ResourceType.User), userDto);
            // If this throws UserAlreadyExistsException or any other Exception, GlobalExceptionHandler handles it.
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping(UrlMapping.USER_ID_VARIABLE)
    public ResponseEntity<ApiResponse<UserDto>> updateUser(@PathVariable Long userId, @RequestBody UserUpdateRequest request) {
            UserDto updatedUser = userService.updateUser(userId, request);
            ApiResponse<UserDto> response = new ApiResponse<>(FeedbackMessage.updateSuccess(ResourceType.User), updatedUser);
            return ResponseEntity.ok(response);
    }

    // GET All Users: GET /api/v1/users
    @GetMapping
    public ResponseEntity<ApiResponse<List<UserDto>>> getAllUsers() {
        List<UserDto> usersDtoList = userService.getAllUsers();
        ApiResponse<List<UserDto>> response = new ApiResponse<>(FeedbackMessage.createSuccess(ResourceType.User), usersDtoList);
        return ResponseEntity.ok(response);
    }

    @GetMapping(UrlMapping.USER_ID_VARIABLE)
    public ResponseEntity<ApiResponse<UserDto>> getUserById(@PathVariable Long userId) {
        UserDto userDto = userService.getUserById(userId);
        ApiResponse<UserDto> response = new ApiResponse<>(FeedbackMessage.foundSuccess(ResourceType.User), userDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(UrlMapping.USER_ID_VARIABLE)
    public ResponseEntity<ApiResponse<UserDto>> deleteUserById(@PathVariable Long userId) {
        userService.deleteUserById(userId);
        ApiResponse<UserDto> response = new ApiResponse<>(FeedbackMessage.deleteSuccess(ResourceType.User), null);
        return ResponseEntity.ok(response);
    }
}
