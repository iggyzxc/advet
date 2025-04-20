package com.macadev.advet.config;

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
        return modelMapper;
    }

    @Bean // Define the password encoder bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}