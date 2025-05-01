package com.macadev.advet.service.pet;

import com.macadev.advet.dto.request.pet.PetRequestDto;
import com.macadev.advet.dto.request.pet.PetUpdateDto;
import com.macadev.advet.dto.response.PetDto;
import com.macadev.advet.exception.ResourceNotFoundException;
import com.macadev.advet.model.Pet;
import com.macadev.advet.model.User;
import com.macadev.advet.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PetService implements IPetService {
    private final PetRepository petRepository;
    private final ModelMapper modelMapper;
    private static final Logger log = LoggerFactory.getLogger(PetService.class);

    @Override
    @Transactional
    public List<PetDto> savePets(List<PetRequestDto> request) {
        // createPet and updatePet methods generally need to work with the full entity in the service layer to
        // apply logic and persist changes.
        List<Pet> petsToSave = request.stream()
                .map(petDto -> modelMapper.map(petDto, Pet.class)) // Map pet DTO -> new Entity
                .toList();

        // Save the new entities and map them to DTOs
        return petRepository.saveAll(petsToSave)
                .stream()
                .map(pet -> modelMapper.map(pet, PetDto.class))
                .toList();
    }

    // Both getAllPets and getPetById methods return DTOs directly from the repository for
    // better performance, avoiding lazy loading issues and to avoid unnecessary mapping

    @Override
    @Transactional(readOnly = true)
    public List<PetDto> getAllPets() {
        // Custom query to fetch all PetDtos
        return petRepository.findAllPetDtos();
    }

    @Override
    @Transactional(readOnly = true)
    public PetDto getPetById(Long petId) {
        // Custom query to fetch PetDto by ID
        return petRepository.findPetDtoById(petId)
                .orElseThrow(() -> new ResourceNotFoundException("Pet", petId));
    }

    @Override
    @Transactional
    public PetDto updatePet(Long petId, PetUpdateDto request) {
        log.debug("Attempting to update pet with ID: {}", petId);
        // Fetch the Pet entity
        Pet existingPet = petRepository.findById(petId)
                .orElseThrow(()->{
                        log.warn("Pet not found for update with ID: {}", petId);
                        return new ResourceNotFoundException("Pet", petId);
                });

        // Apply changes from the request to the existing entity
        modelMapper.map(request, existingPet);
        log.debug("Applied updates from DTO to Pet entity with ID: {}", petId);

        // Save the updated entity
        Pet savedPet = petRepository.save(existingPet);
        log.debug("Saved updated Pet entity with ID: {}", savedPet.getId());

        PetDto responseDto = modelMapper.map(savedPet, PetDto.class);
        log.debug("Mapped basic fields from Pet entity to PetDto for ID: {}", savedPet.getId());
//        responseDto.setId(savedPet.getId());
//        responseDto.setName(savedPet.getName());
//        responseDto.setType(savedPet.getType());
//        responseDto.setColor(savedPet.getColor());
//        responseDto.setBreed(savedPet.getBreed());
//        responseDto.setAge(savedPet.getAge());
//        responseDto.setGender(savedPet.getGender());

        // Manually handle ownerFullName while the session is active
        User owner = savedPet.getOwner(); // Get the owner from the SAVED entity
        if (owner != null) {
            // Access owner details here - guaranteed to work within the transaction
            String firstName = owner.getFirstName();
            String lastName = owner.getLastName();
            responseDto.setOwnerFullName(String.format("%s %s", firstName, lastName));
            log.debug("Mapped ownerFullName: {}", responseDto.getOwnerFullName());
        } else {
            responseDto.setOwnerFullName(null);
            log.debug("Pet owner is null, ownerFullName set to null.");
        }

        // Return the manually constructed DTO
        log.info("Successfully updated pet with ID: {}", savedPet.getId());
        return responseDto;
    }

    @Override
    @Transactional
    public void deletePetById(Long petId) {
        log.debug("Attempting to delete pet with ID: {}", petId);
        petRepository.findById(petId)
                .ifPresentOrElse(pet -> {
                    petRepository.delete(pet);
                    log.info("Successfully deleted pet with ID: {}", petId);
                    },
                        () -> {
                    log.warn("Pet not found for deletion with ID: {}", petId);
                    throw new ResourceNotFoundException("Pet", petId);
                });
    }
}
