package com.tender.controller;

import com.tender.entity.Image;
import com.tender.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;

import java.io.IOException;

@RestController
@RequestMapping("/images")
@Slf4j
public class ImageController {

    @Autowired
    private ImageService imageService;

    private static final Logger logger = LoggerFactory.getLogger(ImageController.class);

    // Endpoint for uploading images
    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
        // Check if file is empty
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please select a file to upload");
        }

        // Check if file size exceeds limit (e.g., 5MB)
        if (file.getSize() > 5 * 1024 * 1024) {
            return ResponseEntity.badRequest().body("File size exceeds the limit of 5MB");
        }

        try {
            // Saving the image using ImageService
            Image savedImage = imageService.saveImage(file);
            logger.info("Image saved successfully with id: {}", savedImage.getId());
            // Returning response with the saved image
            return ResponseEntity.ok(savedImage);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            logger.error("Failed to upload image", e);
            throw new RuntimeException(e);
        }
    }

    // Endpoint for retrieving images by ID
    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getImageById(@PathVariable Long id) {
        // Check if ID is null or zero
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().build();
        }

        // Retrieving image by ID using ImageService
        Image image = imageService.getImage(id);
        if (image != null) {
            // Returning response with image data
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(image.getData());
        } else {
            // If image not found, returning 404
            return ResponseEntity.notFound().build();
        }
    }
}
