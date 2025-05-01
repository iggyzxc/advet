package com.macadev.advet.factory;

import com.macadev.advet.dto.request.user.UserRegistrationRequestDto;
import com.macadev.advet.model.Patient;
import com.macadev.advet.model.User;
import com.macadev.advet.enums.UserType;
import com.macadev.advet.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientFactory {

    private final PatientRepository patientRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public User createPatient(UserRegistrationRequestDto request) {
        Patient patient = new Patient();
        modelMapper.map(request, patient);
        // Encode password before save
        patient.setPassword(passwordEncoder.encode(patient.getPassword()));
        patient.setUserType(UserType.PATIENT); // Explicitly set type
        return patientRepository.save(patient);
    }
}
