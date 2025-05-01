package com.macadev.advet.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "patient_id")
@Table(name = "patients")
public class Patient extends User{

    @OneToMany(
            mappedBy = "owner", // MUST match the 'owner' field name in the Pet class
            cascade = CascadeType.ALL, // Deleting a patient deletes their pets. Use carefully!
            fetch = FetchType.LAZY) // Always use LAZY for collections
    private List<Pet> pets = new ArrayList<>(); // Initialize the list

    @OneToMany(
            mappedBy = "patient", // MUST match the 'patient' field name in the Appointment class
            cascade = CascadeType.ALL, // Deleting a patient deletes their appointments. Use carefully!
            fetch = FetchType.LAZY) // Always use LAZY for collections
    private List<Appointment> appointments = new ArrayList<>(); // Initialize the list
}
