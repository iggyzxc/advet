package com.macadev.advet.service.image;

import com.macadev.advet.dto.response.ImageDto;
import com.macadev.advet.exception.AppException;
import com.macadev.advet.exception.InvalidInputException;
import com.macadev.advet.exception.ResourceNotFoundException;
import com.macadev.advet.model.Image;
import com.macadev.advet.model.User;
import com.macadev.advet.repository.ImageRepository;
import com.macadev.advet.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {

    private final ImageRepository imageRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private static final Logger log = LoggerFactory.getLogger(ImageService.class);


    @Override
    @Transactional
    public ImageDto saveImage(MultipartFile file, Long userId) throws IOException {
        log.debug("Attempting to save image for user ID: {}", userId);
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File cannot be null or empty.");
        }

        // Find the user uploading the image
        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.warn("User not found for image upload for ID: {}", userId);
                    return new ResourceNotFoundException("User", userId);
                });

        if (user.getImage() != null) {
            log.warn("User {} already has an image (ID: {}). Use updateImage to replace.", userId, user.getImage().getId());
            throw new InvalidInputException("User already has a profile image. Use the update operation instead.");
        }

        Image image = new Image();
        log.debug("Creating new image entity for user ID: {}", userId);

        image.setContentType(file.getContentType());
        // Clean and set filename
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        image.setFileName(fileName);

        // --- Handling Blob data ---
        try {
            // Create a Blob object from the file's bytes
            Blob imageBlob = new SerialBlob(file.getBytes());
            image.setImageData(imageBlob);
        } catch (SQLException | IOException e) { // Catch potential exceptions
            log.error("Error processing image data for user {}: {}", userId, e.getMessage(), e);
            // Wrap checked exceptions in a runtime exception
            throw new AppException("Error processing uploaded file data.", e);
        }

        // Save the Image entity FIRST
        Image savedImage = imageRepository.save(image);
        log.info("Successfully saved new image with ID: {} for user ID: {}", savedImage.getId(), userId);

        // 4. Associate image with user then save user
        user.setImage(savedImage);
        User savedUser = userRepository.save(user);
        log.debug("Updated user {} with new image ID {}", userId, savedImage.getId());

        ImageDto imageDto = modelMapper.map(savedImage, ImageDto.class);

        // Double check if timestamp is populated now
        if (imageDto.getUploadedAt() == null && savedUser.getCreatedAt() != null) {
            log.warn("Timestamp was set on entity ({}) but null in DTO after mapping!", savedImage.getUploadedAt());
            // Manually set if needed (indicates mapping issue)
            imageDto.setUploadedAt(savedUser.getCreatedAt());
        } else if (imageDto.getUploadedAt() == null) {
            log.warn("Timestamp still null after save and mapping for image ID {}", savedImage.getId());
        }

        // Return the DTO
        return imageDto;
    }

    @Override
    @Transactional(readOnly = true) // Read-only transaction
    public ImageDto getImageById(Long imageId) {
        log.debug("Attempting to get image by ID: {}", imageId);
        Image image = imageRepository.findById(imageId).orElseThrow(
                () -> {
                    log.warn("Image not found for ID: {}", imageId);
                    return new ResourceNotFoundException("Image", imageId);
                }
        );
        return modelMapper.map(image, ImageDto.class);
    }

    @Override
    @Transactional(readOnly = true) // Read-only transaction
    public byte[] getImageData(Long imageId) {
        log.debug("Attempting to get image data for ID: {}", imageId);

        // Fetch the Image entity
        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> {
                    log.warn("Image not found when fetching data for ID: {}", imageId);
                    return new ResourceNotFoundException("Image", imageId);
                        });

        // Extract byte data from Blob, handling potential SQLException
        try {
            Blob imageBlob = image.getImageData();
            if (imageBlob == null) {
                log.warn("Image Blob data is null for ID: {}", imageId);
                return new byte[0]; // Return an empty array if the Blob field is null
            }

            int blobLength = (int) imageBlob.length();
            if (blobLength == 0) {
                log.warn("Image Blob data is empty for ID: {}", imageId);
                return new byte[0];
            }
            byte[] data = imageBlob.getBytes(1, blobLength); // Get bytes from blob
            imageBlob.free(); // IMPORTANT: Release blob resources if applicable

            log.debug("Returning image data ({} bytes) for ID: {}", data.length, imageId);
            return data;

        } catch (SQLException e) {  //  // Catch SQLException from Blob operations
            log.error("SQL Error retrieving image byte data for ID {}: {}", imageId, e.getMessage(), e);
            // Wrap in a runtime exception
            throw new AppException("Could not retrieve image data due to database error for ID: " + imageId, e);
        } catch (NullPointerException e) { // Catch potential NPE if image.getImageData() returns null unexpectedly
            log.error("Null pointer while retrieving image byte data for ID {}: {}", imageId, e.getMessage(), e);
            throw new AppException("Could not retrieve image data due to null value for ID: " + imageId, e);
        }
    }


    @Override
    @Transactional
    public ImageDto updateImage(Long imageId, MultipartFile file) throws IOException { // Accept MultipartFile for update
        log.debug("Attempting to update image with ID: {} using file: {}", imageId, file.getOriginalFilename());
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File cannot be null or empty for update.");
        }

        // Fetch the existing Image entity
        Image existingImage = imageRepository.findById(imageId)
                .orElseThrow(() -> {
                    log.warn("Image not found for update with ID: {}", imageId);
                    return new ResourceNotFoundException("Image", imageId);
                });

        // Update metadata and data from the file
        try {
            // Clean filename (optional, but good practice)
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            existingImage.setFileName(fileName); // Update filename if stored
            existingImage.setContentType(file.getContentType()); // Update content type

            // Convert file bytes to Blob
            Blob imageBlob = new SerialBlob(file.getBytes());
            existingImage.setImageData(imageBlob); // Set the Blob field

        } catch (SQLException | IOException e) {
            log.error("SQL/IO Error processing file for image update ID {}: {}", imageId, e.getMessage(), e);
            throw new AppException("Could not update image data due to processing error for ID: " + imageId, e);
        }

        // Save the updated entity
        Image updatedImage = imageRepository.save(existingImage);
        log.info("Successfully updated image with ID: {}", updatedImage.getId());

        // Map to DTO before returning
        return modelMapper.map(updatedImage, ImageDto.class);
    }

    @Override
    @Transactional
    public void deleteImage(Long imageId) {
        log.debug("Attempting to delete image with ID: {}", imageId);
        //Find first, then delete it if present
        imageRepository.findById(imageId)
                .ifPresentOrElse(image -> {
                    log.info("Image found with ID: {}. Deleting...", imageId);
                    User user = image.getUser();

                    // You need to remove all pointers to an object
                    // before you can safely delete the object itself.
                    // Because the User holds the pointer (image_id column),
                    // you need to update the User first to remove that pointer
                    // before deleting the Image.
                    if (user != null) {
                        log.debug("Detaching image from user ID: {}", user.getId());
                        user.setImage(null); //  Remove the link from the owning side (User)
                        userRepository.save(user); // Updates the User object in memory
                    }
                    // Once the foreign key reference in the user's table is removed (set to NULL),
                    // it's safe to delete the Image record.
                    // No database constraints will be violated, and no orphaned references remain.
                    imageRepository.delete(image);
                    log.info("Successfully deleted image with ID: {}", imageId);
                }, () -> {
                    log.warn("Image not found for deletion with ID: {}", imageId);
                    throw new ResourceNotFoundException("Image", imageId);
                });
    }
}
