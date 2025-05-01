package com.macadev.advet.service.image;

import com.macadev.advet.model.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {
    @Override
    public Image saveImage(MultipartFile file, Long userId) {
        return null;
    }

    @Override
    public Optional<Image> getImage(Long imageId) {
        return Optional.empty();
    }

    @Override
    public byte[] getImageData(Long imageId) {
        return new byte[0];
    }

    @Override
    public Image updateImage(Long imageId, byte[] imageData) {
        return null;
    }

    @Override
    public void deleteImage(Long imageId) {

    }
}
