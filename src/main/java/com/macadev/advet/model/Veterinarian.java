package com.macadev.advet.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "veterinarian_id")
@Table(name = "veterinarians")
public class Veterinarian extends User{
    private String specialization;

    @OneToMany(
            mappedBy = "veterinarian", // MUST match the 'veterinarian' field name in the Appointment class
            orphanRemoval = true, // Example: Removing an appointment from this list deletes it from DB.
            fetch = jakarta.persistence.FetchType.LAZY) // Always use LAZY for collections
    private List<Appointment> appointments = new ArrayList<>(); // Initialize the list
}
