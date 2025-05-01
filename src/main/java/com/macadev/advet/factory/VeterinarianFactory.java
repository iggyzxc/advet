package com.macadev.advet.factory;

import com.macadev.advet.dto.request.user.UserRegistrationRequestDto;
import com.macadev.advet.model.User;
import com.macadev.advet.enums.UserType;
import com.macadev.advet.model.Veterinarian;
import com.macadev.advet.repository.VeterinarianRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VeterinarianFactory {

    private final VeterinarianRepository veterinarianRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public User createVeterinarian(UserRegistrationRequestDto request) {
        Veterinarian veterinarian = new Veterinarian();
        modelMapper.map(request, veterinarian);
        veterinarian.setSpecialization(request.getSpecialization());
        veterinarian.setUserType(UserType.VET);
        veterinarian.setPassword(passwordEncoder.encode(veterinarian.getPassword()));
        return veterinarianRepository.save(veterinarian);
    }
}

