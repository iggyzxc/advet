package com.macadev.advet.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PetDto {
    private Long id;
    private String name;
    private String type; // e.g., dog, cat
    private String color;
    private String breed;
    private int age;
    private String gender;
    private String ownerFullName;

    public PetDto(Long id, String name, String type, String color, String breed, int age, String gender, String ownerFirstName, String ownerLastName) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.color = color;
        this.breed = breed;
        this.age = age;
        this.gender = gender;
        this.ownerFullName = (ownerFirstName != null && ownerLastName != null)
                ? ownerFirstName + " " + ownerLastName
                : null;
    }
}
