package com.macadev.advet.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ImageDto {
    private Long id;
    private String contentType;
    private String fileName;
    private LocalDateTime uploadedAt;
}
