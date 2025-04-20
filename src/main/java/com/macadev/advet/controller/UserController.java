package com.macadev.advet.controller;

import com.macadev.advet.dto.request.UserUpdateRequest;
import com.macadev.advet.dto.response.UserDto;
import com.macadev.advet.dto.request.UserRegistrationRequest;
import com.macadev.advet.dto.response.ApiResponse;

import com.macadev.advet.service.UserService;
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
    public ResponseEntity<ApiResponse<UserDto>> add(@RequestBody UserRegistrationRequest request) {
            // No try-catch needed here!
            UserDto userDto = userService.register(request);
            // If this throws UserAlreadyExistsException or any other Exception, GlobalExceptionHandler handles it.
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse(FeedbackMessage.REGISTER_SUCCESS, userDto));
    }

    // UPDATE User: PUT /api/v1/users/{userId}
    @PutMapping(UrlMapping.USER_ID_VARIABLE)
    public ResponseEntity<ApiResponse<UserDto>> update(@PathVariable Long userId, @RequestBody UserUpdateRequest request) {
            UserDto userDto = userService.update(userId, request);
            return ResponseEntity.ok(new ApiResponse(FeedbackMessage.UPDATE_SUCCESS, userDto));
    }

    // GET User by ID: GET /api/v1/users/{userId}
    @GetMapping(UrlMapping.USER_ID_VARIABLE)
    public ResponseEntity<ApiResponse<UserDto>> getUserById(@PathVariable Long userId) {
        UserDto userDto = userService.getUserById(userId);
        return ResponseEntity.ok(new ApiResponse(FeedbackMessage.FOUND_SUCCESS, userDto));
    }

    // DELETE User: DELETE /api/v1/users/{userId}
    @DeleteMapping(UrlMapping.USER_ID_VARIABLE)
    public ResponseEntity<ApiResponse<UserDto>> deleteUser(@PathVariable Long userId) {
        userService.delete(userId);
        return ResponseEntity.noContent().build(); // Return 204
    }

    // GET All Users: GET /api/v1/users
    @GetMapping
    public ResponseEntity<ApiResponse<List<UserDto>>> getAllUsers() {
        List<UserDto> usersDtos = userService.getAllUsers();
        return ResponseEntity.ok(new ApiResponse(FeedbackMessage.FOUND_SUCCESS, usersDtos));
    }
}
