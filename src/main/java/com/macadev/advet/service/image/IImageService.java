package com.macadev.advet.service.image;

import com.macadev.advet.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface IImageService {
    Image saveImage(MultipartFile file, Long userId);
    Optional<Image> getImage(Long imageId);
    byte[] getImageData(Long imageId);
    Image updateImage(Long imageId, byte[] imageData);
    void deleteImage(Long imageId);
}
