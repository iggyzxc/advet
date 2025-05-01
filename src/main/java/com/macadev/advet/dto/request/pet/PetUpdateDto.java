package com.macadev.advet.dto.request.pet;

import lombok.Data;

@Data
public class PetUpdateDto {
    private String name;
    private String type; // e.g., dog, cat
    private String color;
    private String breed;
    private int age;
    private String gender;
}
