package com.macadev.advet.service;

import com.macadev.advet.dto.request.UserRegistrationRequest;
import com.macadev.advet.dto.request.UserUpdateRequest;
import com.macadev.advet.dto.response.UserDto;
import com.macadev.advet.exception.UserException;
import com.macadev.advet.factory.UserFactory;
import com.macadev.advet.model.User;
import com.macadev.advet.model.Veterinarian;
import com.macadev.advet.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{
    private final UserRepository userRepository;
    private final UserFactory userFactory;
    private final ModelMapper modelMapper;

    @Override
    public UserDto register(UserRegistrationRequest request) {
        // Calls factory to create+save specific user type (Admin, Patient, Vet)
        // Saves the user to the DB and returns the created user
        User user = userFactory.createUser(request);
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto update(Long userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException.ResourceNotFoundException("User not found with ID: " + userId));
//        user.setFirstName(request.getFirstName());
//        user.setLastName(request.getLastName());
//        user.setGender(request.getGender());
//        user.setPhoneNumber(request.getPhoneNumber());
        modelMapper.map(request, user);

        // Check if the user is a veterinarian, then update the specialization
        if (user instanceof Veterinarian) {
            Veterinarian vet = (Veterinarian) user;
            // Check if request.getSpecialization() is provided
            if(request.getSpecialization() != null) {
                vet.setSpecialization(request.getSpecialization());
            }
        }else {
            // If specialization was provided for a non-vet, throw error
            if (request.getSpecialization() != null) {
                throw new UserException.InvalidUserException("Specialization can only be set for Veterinarian users.");
            }
        }
        // Save the updated user
        userRepository.save(user);
        // Map the final saved user state to DTO
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto getUserById(Long userId) {
        User user =  userRepository.findById(userId)
                .orElseThrow(() -> new UserException.ResourceNotFoundException("User not found with ID: " + userId));
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public void delete(Long userId) {
        userRepository.findById(userId)
                .ifPresentOrElse(userRepository::delete, () -> {
                    throw new UserException.ResourceNotFoundException("User not found with ID: " + userId);
        });
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map((user) -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }
}

