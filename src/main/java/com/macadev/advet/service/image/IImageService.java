package com.macadev.advet.service.image;

import com.macadev.advet.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public interface IImageService {
    Image saveImage(MultipartFile file, Long userId) throws IOException, SQLException;
    Optional<Image> getImageById(Long imageId);
    byte[] getImageData(Long imageId);
    Image updateImage(Long imageId, byte[] imageData);
    void deleteImage(Long imageId);
}
