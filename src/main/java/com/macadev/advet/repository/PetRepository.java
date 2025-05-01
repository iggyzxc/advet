package com.macadev.advet.repository;

import com.macadev.advet.dto.response.PetDto;
import com.macadev.advet.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    //Custom query selecting directly into PetDto constructor
    @Query("SELECT new com.macadev.advet.dto.response.PetDto(p.id, p.name, p.type, p.color, p.breed, p.age, p.gender, o.firstName, o.lastName) " +
    "FROM Pet p LEFT JOIN p.owner o WHERE p.id = :petId")
    Optional<PetDto> findPetDtoById(@Param("petId") Long petId);

    @Query("SELECT new com.macadev.advet.dto.response.PetDto(p.id, p.name, p.type, p.color, p.breed, p.age, p.gender, o.firstName, o.lastName) " +
            "FROM Pet p LEFT JOIN p.owner o")
    List<PetDto> findAllPetDtos();
}
