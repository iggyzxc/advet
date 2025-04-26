package com.macadev.advet.config;

import com.macadev.advet.dto.response.user.AdminDto;
import com.macadev.advet.dto.response.user.PatientDto;
import com.macadev.advet.dto.response.user.UserDto;
import com.macadev.advet.dto.response.user.VeterinarianDto;
import com.macadev.advet.model.Admin;
import com.macadev.advet.model.Patient;
import com.macadev.advet.model.User;
import com.macadev.advet.model.Veterinarian;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ApplicationConfig {

    @Bean
    public ModelMapper modelMapper() {
        // Create a standard ModelMapper instance
        ModelMapper modelMapper = new ModelMapper();

        // Access its configuration settings
        modelMapper.getConfiguration()
                // Enable skipping null values during mapping
                .setSkipNullEnabled(true) // Add this line
                // Set the strategy for matching source/destination fields
                .setMatchingStrategy(MatchingStrategies.STRICT); // Example: Use strict matching

        // Define type mappings for inheritance
        // Define the base mapping for common fields
        modelMapper.createTypeMap(User.class, UserDto.class);

        // Define subclass mappings and include the base mapping
        modelMapper.createTypeMap(Patient.class, PatientDto.class)
                .includeBase(User.class, UserDto.class); // Inherit User -> UserDto mapping

        modelMapper.createTypeMap(Veterinarian.class, VeterinarianDto.class)
                .includeBase(User.class, UserDto.class);

        modelMapper.createTypeMap(Admin.class, AdminDto.class)
                .includeBase(User.class, UserDto.class); // Inherit User -> UserDto mapping
        return modelMapper;
    }

    @Bean // Define the password encoder bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}