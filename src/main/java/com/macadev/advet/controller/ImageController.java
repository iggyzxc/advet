package com.macadev.advet.controller;

import com.macadev.advet.dto.response.ApiResponse;
import com.macadev.advet.dto.response.ImageDto;
import com.macadev.advet.enums.ResourceType;
import com.macadev.advet.service.image.IImageService;
import com.macadev.advet.util.FeedbackMessage;
import com.macadev.advet.util.UrlMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping(UrlMapping.IMAGES_BASE)
public class ImageController {
    private final IImageService imageService;

    @PostMapping
    public ResponseEntity<ApiResponse<ImageDto>> uploadImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("userId") Long userId) throws IOException {
            ImageDto savedImage = imageService.saveImage(file, userId);
            ApiResponse<ImageDto> response = new ApiResponse<>(FeedbackMessage.createSuccess(ResourceType.IMAGE), savedImage);
            return new ResponseEntity<>(response, HttpStatus.CREATED); // 201 Created status
    }

    @GetMapping(UrlMapping.IMAGE_ID_VARIABLE)
    public ResponseEntity<ApiResponse<ImageDto>> getImage(@PathVariable Long imageId) {
            ImageDto imageDto = imageService.getImageById(imageId);
            ApiResponse<ImageDto> response = new ApiResponse<>(FeedbackMessage.foundSuccess(ResourceType.IMAGE), imageDto);
            return ResponseEntity.ok(response);

    }

    @PutMapping(UrlMapping.IMAGE_ID_VARIABLE)
    public ResponseEntity<ApiResponse<ImageDto>> updateImage(
            @PathVariable Long imageId,
            @RequestParam MultipartFile file) throws IOException{
        //    Call the service method, passing the image ID and the uploaded file
        //    The service now handles extracting bytes and updating the Blob.
        //    It will throw ResourceNotFoundException if imageId is invalid (handled globally).
        //    It will throw AppException if file processing fails (handled globally).
        ImageDto updatedImage = imageService.updateImage(imageId, file);

        // Create the success response
        ApiResponse<ImageDto> response = new ApiResponse<>(FeedbackMessage.createSuccess(ResourceType.IMAGE), updatedImage);

        // Return 200 OK with the response body
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(UrlMapping.IMAGE_ID_VARIABLE)
    public ResponseEntity<ApiResponse<ImageDto>> deleteImage(@PathVariable Long imageId) {
            // Let GlobalExceptionHandler handle ResourceNotFoundException
            imageService.deleteImage(imageId);
            return ResponseEntity.ok(new ApiResponse<>(FeedbackMessage.deleteSuccess(ResourceType.IMAGE), null));
    }
}
