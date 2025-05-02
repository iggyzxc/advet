package com.macadev.advet.service.image;

import com.macadev.advet.dto.response.ImageDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface IImageService {
    ImageDto saveImage(MultipartFile file, Long userId) throws IOException;
    ImageDto getImageById(Long imageId);
    byte[] getImageData(Long imageId);
    ImageDto updateImage(Long imageId, MultipartFile file) throws IOException;
    void deleteImage(Long imageId);
}
