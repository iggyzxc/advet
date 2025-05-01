package com.macadev.advet.service.appointment;

import com.macadev.advet.dto.request.appointment.AppointmentRequestDto;
import com.macadev.advet.dto.request.appointment.AppointmentUpdateRequestDto;
import com.macadev.advet.dto.request.pet.PetRequestDto;
import com.macadev.advet.dto.response.AppointmentDto;
import com.macadev.advet.enums.AppointmentStatus;
import com.macadev.advet.exception.InvalidInputException;
import com.macadev.advet.exception.ResourceNotFoundException;
import com.macadev.advet.model.Appointment;
import com.macadev.advet.model.Pet;
import com.macadev.advet.model.User;
import com.macadev.advet.repository.AppointmentRepository;
import com.macadev.advet.repository.PetRepository;
import com.macadev.advet.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AppointmentService implements IAppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final PetRepository petRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public AppointmentDto createAppointment(AppointmentRequestDto request) {
        // Fetch Patient and Veterinarian users
        User patient = userRepository.findById(request.getPatientId()).orElseThrow(()-> new ResourceNotFoundException("Patient", request.getPatientId()));
        User veterinarian = userRepository.findById(request.getVeterinarianId()).orElseThrow(()-> new ResourceNotFoundException("Veterinarian", request.getVeterinarianId()));

        // Create NEW Pet entities from the DTO data
        List<Pet> petList = new ArrayList<>();
        if (request.getPets() == null || request.getPets().isEmpty() ) {
            throw new InvalidInputException("At least one pet must be provided for the appointment");
        }

        for (PetRequestDto petDto : request.getPets()) {
            // Map the PetRequestDto to a new Pet entity
            Pet pet = modelMapper.map(petDto, Pet.class);
            pet.setOwner(patient);
            petList.add(pet);
            // Note: Save the pets LATER after associating with the appointment
        }

        // Create a new appointment entity
        Appointment appointment = new Appointment();

        // Set fields from the DTO
        appointment.setReason(request.getReason());
        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setAppointmentTime(request.getAppointmentTime());

        // Associate Patient and Veterinarian using helper methods
        appointment.addPatient(patient);
        appointment.addVeterinarian(veterinarian);

        // Generate and set appointment number
        appointment.setAppointmentNumber();

        // Set the initial status
        appointment.setStatus(AppointmentStatus.PENDING);

        // Save the appointment first to get its id
        // Because the foreign key (appointment_id) is in the Pets table
        Appointment savedAppointment = appointmentRepository.save(appointment);

        // Associate Pets with the saved appointment, then save Pets
        for (Pet pet : petList) {
            pet.setAppointment(savedAppointment); // Set the ManyToOne side in Pet
        }

        // Save all the newly created/updated Pet entities at once
        List<Pet> savedPets = petRepository.saveAll(petList);

        savedAppointment.setPets(savedPets); // Set the OneToMany side in Appointment

        // Map the final state of the saved appointment to the response DTO
        return modelMapper.map(savedAppointment, AppointmentDto.class);
    }

    @Override
    @Transactional
    public List<AppointmentDto> getAllAppointments() {
        List<Appointment> appointmentList = appointmentRepository.findAll();
        return appointmentList.stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentDto.class))
                .toList();
    }

    @Override
    @Transactional
    public AppointmentDto getAppointmentById(Long appointmentId) {
        Appointment appointment =  appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment", appointmentId));
        return modelMapper.map(appointment, AppointmentDto.class);
    }

    @Override
    @Transactional
    public AppointmentDto getByAppointmentNumber(String appointmentNumber) {
        Appointment appointment = appointmentRepository.findByAppointmentNumber(appointmentNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment", appointmentNumber));
        return modelMapper.map(appointment, AppointmentDto.class);
    }

    @Override
    @Transactional
    public AppointmentDto updateAppointment(Long appointmentId, AppointmentUpdateRequestDto request) {
        AppointmentDto appointmentDto = getAppointmentById(appointmentId);
        // if the appointment is not pending, it cannot be updated
        if (!Objects.equals(appointmentDto.getStatus(), AppointmentStatus.PENDING)) {
            throw new IllegalStateException("Sorry, this appointment can no longer be updated");
        }
        Appointment appointment = modelMapper.map(appointmentDto, Appointment.class);
        modelMapper.map(request, appointment);
        appointmentRepository.save(appointment);
        AppointmentDto updatedAppointmentDto = modelMapper.map(appointment, AppointmentDto.class);
        return modelMapper.map(updatedAppointmentDto, AppointmentDto.class);
    }

    @Override
    @Transactional
    public void deleteAppointmentById(Long appointmentId) {
        appointmentRepository.findById(appointmentId).ifPresentOrElse(
                appointmentRepository::delete,
                () -> {throw new ResourceNotFoundException("Appointment", appointmentId);
        });
    }
}
