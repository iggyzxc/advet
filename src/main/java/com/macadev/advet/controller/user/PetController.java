package com.macadev.advet.controller.user;

import com.macadev.advet.dto.request.pet.PetRequestDto;
import com.macadev.advet.dto.request.pet.PetUpdateDto;
import com.macadev.advet.dto.response.ApiResponse;
import com.macadev.advet.dto.response.PetDto;
import com.macadev.advet.enums.ResourceType;
import com.macadev.advet.service.pet.PetService;
import com.macadev.advet.util.FeedbackMessage;
import com.macadev.advet.util.UrlMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(UrlMapping.PETS_BASE)
public class PetController {
    private final PetService petService;

    @PostMapping
    public ResponseEntity<ApiResponse<List<PetDto>>> savePets(@RequestBody List<PetRequestDto> request) {
        List<PetDto> createdPet = petService.savePets(request);
        ApiResponse<List<PetDto>> response = new ApiResponse<>(FeedbackMessage.createSuccess(ResourceType.PET), createdPet);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PetDto>>> getAllPets() {
        List<PetDto> petList = petService.getAllPets();
        ApiResponse<List<PetDto>> response = new ApiResponse<>(FeedbackMessage.foundSuccess(ResourceType.PET), petList);
        return ResponseEntity.ok(response);
    }

    @GetMapping(UrlMapping.PET_ID_VARIABLE)
    public ResponseEntity<ApiResponse<PetDto>> getPetById(@PathVariable Long petId) {
        PetDto pet = petService.getPetById(petId);
        ApiResponse<PetDto> response = new ApiResponse<>(FeedbackMessage.foundSuccess(ResourceType.PET), pet);
        return ResponseEntity.ok(response);
    }

    @PutMapping(UrlMapping.PET_ID_VARIABLE)
    public ResponseEntity<ApiResponse<PetDto>> updatePet(@PathVariable Long petId, @RequestBody PetUpdateDto request) {
        PetDto updatedPet = petService.updatePet(petId, request);
        ApiResponse<PetDto> response = new ApiResponse<>(FeedbackMessage.updateSuccess(ResourceType.PET), updatedPet);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(UrlMapping.PET_ID_VARIABLE)
    public ResponseEntity<ApiResponse<PetDto>> deletePetById(@PathVariable Long petId) {
        petService.deletePetById(petId);
        ApiResponse<PetDto> response = new ApiResponse<>(FeedbackMessage.deleteSuccess(ResourceType.PET), null);
        return ResponseEntity.ok(response);
    }
}
