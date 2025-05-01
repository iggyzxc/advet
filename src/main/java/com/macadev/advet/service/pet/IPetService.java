package com.macadev.advet.service.pet;

import com.macadev.advet.dto.request.pet.PetRequestDto;
import com.macadev.advet.dto.request.pet.PetUpdateDto;
import com.macadev.advet.dto.response.PetDto;

import java.util.List;

public interface IPetService {
    List<PetDto> savePets(List<PetRequestDto> pets);
    List<PetDto> getAllPets();
    PetDto getPetById(Long petId);
    PetDto updatePet(Long petId, PetUpdateDto pet);
    void deletePetById(Long petId);
}
