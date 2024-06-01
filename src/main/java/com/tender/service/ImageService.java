package com.tender.service;

import com.tender.entity.Image;
import com.tender.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    // Method to save an image
    public Image saveImage(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setData(file.getBytes());
        // Save the image in the database using ImageRepository
        return imageRepository.save(image);
    }

    // Method to get an image by its ID
    public Image getImage(Long id) {
        // Retrieve the image from the database by its ID using ImageRepository
        // If the image exists, return it; otherwise, return null
        return imageRepository.findById(id).orElse(null);
    }

}
