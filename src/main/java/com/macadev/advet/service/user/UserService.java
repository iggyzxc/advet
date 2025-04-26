package com.macadev.advet.service.user;

import com.macadev.advet.dto.request.UserRegistrationRequest;
import com.macadev.advet.dto.request.UserUpdateRequest;
import com.macadev.advet.dto.response.user.UserDto;
import com.macadev.advet.dto.response.user.VeterinarianDto;
import com.macadev.advet.exception.InvalidInputException;
import com.macadev.advet.exception.ResourceNotFoundException;
import com.macadev.advet.factory.UserFactory;
import com.macadev.advet.model.Admin;
import com.macadev.advet.model.Patient;
import com.macadev.advet.model.User;
import com.macadev.advet.model.Veterinarian;
import com.macadev.advet.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{
    private final UserRepository userRepository;
    private final UserFactory userFactory;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public UserDto registerUser(UserRegistrationRequest request) {
        // Calls factory to create and save a specific user type (Admin, Patient, Vet)
        // Saves the user to the DB and returns the created user
        User user = userFactory.createUser(request);
        return mapUserToSpecificDto(user);
    }

    @Override
    @Transactional
    public UserDto updateUser(Long userId, UserUpdateRequest request) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));
        modelMapper.map(request, existingUser);

        // Check if the user is a veterinarian, then update the specialization
        if (existingUser instanceof Veterinarian vet) {
            // Check if request.getSpecialization() is provided
            if(request.getSpecialization() != null) {
                vet.setSpecialization(request.getSpecialization());
            }
        }else {
            // If specialization was provided for a non-vet, throw error
            if (request.getSpecialization() != null) {
                throw new InvalidInputException("Specialization can only be set for Veterinarian users.");
            }
        }
        // Save the updated user
        userRepository.save(existingUser);
        // Map the final saved user state to DTO
        return mapUserToSpecificDto(existingUser);
    }

    @Override
    @Transactional
    public UserDto getUserById(Long userId) {
        User user =  userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));
        return mapUserToSpecificDto(user);
    }

    @Override
    @Transactional
    public void deleteUserById(Long userId) {
        userRepository.findById(userId)
                .ifPresentOrElse(userRepository::delete,
                        () -> {throw new ResourceNotFoundException("User", userId);
        });
    }

    @Override
    @Transactional
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::mapUserToSpecificDto)
                // Replaced '.collect(Collectors.toList())' with '.toList();'
                // which collects into an unmodifiable List
                // The list contains actual instances of PatientDto, VeterinarianDto and/or AdminDto
                .toList();
    }

    // Helper method for mapping
    private UserDto mapUserToSpecificDto(User user) {
        return switch (user) {
            case Veterinarian veterinarian -> modelMapper.map(veterinarian, VeterinarianDto.class);
            case Patient patient -> modelMapper.map(patient, UserDto.class);
            case Admin admin -> modelMapper.map(admin, UserDto.class);
            default -> throw new InvalidInputException("Invalid user type.");
        };
    }
}

