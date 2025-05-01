package com.macadev.advet.factory;

import com.macadev.advet.dto.request.user.UserRegistrationRequestDto;
import com.macadev.advet.model.Admin;
import com.macadev.advet.model.User;
import com.macadev.advet.enums.UserType;
import com.macadev.advet.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminFactory {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    public final ModelMapper modelMapper;

    public User createAdmin(UserRegistrationRequestDto request) {
        Admin admin = new Admin();
        modelMapper.map(request, admin);
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        admin.setUserType(UserType.ADMIN);
        return adminRepository.save(admin);
    }
}
