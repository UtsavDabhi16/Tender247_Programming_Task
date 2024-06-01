package com.tender.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.tender.entity.Image;
import com.tender.service.ImageService;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

@ContextConfiguration(classes = {ImageController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class ImageControllerTest {
    @Autowired
    private ImageController imageController;

    @MockBean
    private ImageService imageService;

    /**
     * Method under test: {@link ImageController#getImageById(Long)}
     */
    @Test
    void testGetImageById() throws Exception {
        // Arrange
        Image image = new Image();
        image.setContentType("text/plain");
        image.setData("AXAXAXAX".getBytes("UTF-8"));
        image.setId(1L);
        image.setName("Name");
        when(imageService.getImage(Mockito.<Long>any())).thenReturn(image);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/images/{id}", 1L);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(imageController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("image/jpeg"))
                .andExpect(MockMvcResultMatchers.content().string("AXAXAXAX"));
    }

    /**
     * Method under test: {@link ImageController#uploadImage(MultipartFile)}
     */
    @Test
    void testUploadImage() throws IOException {

        // Arrange
        ImageController imageController = new ImageController();

        // Act
        ResponseEntity<?> actualUploadImageResult = imageController
                .uploadImage(new MockMultipartFile("Name", new ByteArrayInputStream(new byte[]{})));

        // Assert
        assertEquals("Please select a file to upload", actualUploadImageResult.getBody());
        assertEquals(400, actualUploadImageResult.getStatusCodeValue());
        assertTrue(actualUploadImageResult.getHeaders().isEmpty());
    }
}
