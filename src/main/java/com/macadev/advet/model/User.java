package com.macadev.advet.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name = "users",
        uniqueConstraints = @UniqueConstraint(columnNames = "email")) // Ensure email is unique at DB level
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(length = 10)
    private String gender;

    @Column(name = "mobile", length = 11)
    private String phoneNumber;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false) // Password hash stored here, should not be null
    private String password;

    @Enumerated(EnumType.STRING) // Store enum name ("ADMIN", "VET", "PATIENT") in the database column
    @Column(nullable = false)
    private UserType userType;

    @Column(nullable = false)
    private boolean isEnabled = true; // Default new users to disabled

}
