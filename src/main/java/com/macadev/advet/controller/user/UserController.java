package com.macadev.advet.controller.user;

import com.macadev.advet.dto.request.user.UserUpdateRequestDto;
import com.macadev.advet.dto.response.user.UserDto;
import com.macadev.advet.dto.request.user.UserRegistrationRequestDto;
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
    public ResponseEntity<ApiResponse<UserDto>> createUser(@RequestBody UserRegistrationRequestDto request) {
            // No try-catch needed here!
            UserDto registeredUser = userService.createUser(request);
            ApiResponse<UserDto> response = new ApiResponse<>(FeedbackMessage.createSuccess(ResourceType.USER), registeredUser);
            // If this throws UserAlreadyExistsException or any other Exception, GlobalExceptionHandler handles it.
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // GET All Users: GET /api/v1/users
    @GetMapping
    public ResponseEntity<ApiResponse<List<UserDto>>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        ApiResponse<List<UserDto>> response;
        if (users.isEmpty()) {
            // Returning an empty list doesn't mean there is an error, so we return 200 OK with an empty list
            response = new ApiResponse<>(FeedbackMessage.noResourceFound(ResourceType.USER), null);
        } else {
            response = new ApiResponse<>(FeedbackMessage.createSuccess(ResourceType.USER), users);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping(UrlMapping.USER_ID_VARIABLE)
    public ResponseEntity<ApiResponse<UserDto>> getUserById(@PathVariable Long userId) {
        UserDto user = userService.getUserById(userId);
        ApiResponse<UserDto> response = new ApiResponse<>(FeedbackMessage.foundSuccess(ResourceType.USER), user);
        return ResponseEntity.ok(response);
    }

    @PutMapping(UrlMapping.USER_ID_VARIABLE)
    public ResponseEntity<ApiResponse<UserDto>> updateUser(@PathVariable Long userId, @RequestBody UserUpdateRequestDto request) {
        UserDto updatedUser = userService.updateUser(userId, request);
        ApiResponse<UserDto> response = new ApiResponse<>(FeedbackMessage.updateSuccess(ResourceType.USER), updatedUser);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(UrlMapping.USER_ID_VARIABLE)
    public ResponseEntity<ApiResponse<UserDto>> deleteUserById(@PathVariable Long userId) {
        userService.deleteUserById(userId);
        ApiResponse<UserDto> response = new ApiResponse<>(FeedbackMessage.deleteSuccess(ResourceType.USER), null);
        return ResponseEntity.ok(response);
    }
}
