package com.macadev.advet.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Blob;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50) // Added constraint
    private String contentType;

    @Column(length = 255) // Added constraint
    private String fileName;

    @Lob
    private Blob imageData;

    @CreationTimestamp
    @Column(nullable = false, updatable = false) // Added constraints
    private LocalDateTime uploadedAt;

    @OneToOne(mappedBy = "image", fetch = FetchType.LAZY) // mappedBy points to the 'image' field in User
    private User user;
}
