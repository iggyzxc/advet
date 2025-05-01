package com.macadev.advet.config;

import com.macadev.advet.dto.response.PetDto;
import com.macadev.advet.dto.response.user.AdminDto;
import com.macadev.advet.dto.response.user.PatientDto;
import com.macadev.advet.dto.response.user.UserDto;
import com.macadev.advet.dto.response.user.VeterinarianDto;
import com.macadev.advet.model.*;
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

        // Configure Global Settings
        modelMapper.getConfiguration()
                // Enable skipping null values during mapping (good for updates)
                .setSkipNullEnabled(true)
                // Set the strategy for matching source/destination fields
                .setMatchingStrategy(MatchingStrategies.STRICT); // Use strict matching

        // Define explicit type mappings for User inheritance
        // Define the base mapping for common fields
        modelMapper.createTypeMap(User.class, UserDto.class);

        // Define subclass mappings and include the base mapping
        modelMapper.createTypeMap(Patient.class, PatientDto.class)
                .includeBase(User.class, UserDto.class); // Inherit User -> UserDto mapping

        modelMapper.createTypeMap(Veterinarian.class, VeterinarianDto.class)
                .includeBase(User.class, UserDto.class);

        modelMapper.createTypeMap(Admin.class, AdminDto.class)
                .includeBase(User.class, UserDto.class);

//        // Define Mapping for Pet to PetDto
//        modelMapper.createTypeMap(Pet.class, PetDto.class)
//                .addMappings(mapper ->  // Use curly braces for multi-statement lambda body
//                    // Define the mapping for ownerFullName
//                    // Condition: Only map if the source Pet's owner is not null
//                    mapper.when(ctx -> {
//                                // Use Pattern Matching for instanceof (Java 16+)
//                                Object source = ctx.getSource();
//                                // Check if the source is Pet AND declare 'pet' variable, then check if the owner is not null
//                                return source instanceof Pet pet && pet.getOwner() != null;
//                            })
//                        .map(src -> src.getOwner().getFirstName() + " " + src.getOwner().getLastName(),
//                                PetDto::setOwnerFullName) // Destination setter method reference
//                            );
        return modelMapper;
    }

    @Bean // Define the password encoder bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }
}