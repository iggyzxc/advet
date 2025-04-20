package com.macadev.advet.factory;

import com.macadev.advet.dto.request.UserRegistrationRequest;

import com.macadev.advet.exception.UserException;
import com.macadev.advet.model.User;
import com.macadev.advet.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SimpleUserFactory implements UserFactory {

    private final UserRepository userRepository;
    private final AdminFactory adminFactory;
    private final VeterinarianFactory veterinarianFactory;
    private final PatientFactory patientFactory;

    @Override
    public User createUser(UserRegistrationRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserException.EmailAlreadyExistsException(
                    String.format("User with email %s already exists", request.getEmail())
            );
        }

        switch (request.getUserType()) {
            case ADMIN -> {
                return adminFactory.createAdmin(request);
            }
            case VET -> {
                return veterinarianFactory.createVeterinarian(request);
            }
            case PATIENT -> {
                return patientFactory.createPatient(request);
            }
            default -> throw new UserException.InvalidUserException(
                    "Invalid user type provided:" + request.getUserType()
            );
        }
    }
}
