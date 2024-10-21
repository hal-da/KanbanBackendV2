package at.technikum.springrestbackend.service;

import io.minio.GetObjectArgs;
import io.minio.GetObjectResponse;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.MinioException;
import io.minio.messages.Bucket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MinioImageServiceTest {

    @Mock
    private MinioClient minioClient;

    @Mock
    private MultipartFile multipartFile;

    private MinioImageService minioImageService;

    private final String bucketName = "test-bucket";
    private final String endpoint = "http://localhost:9000";
    private final String accessKey = "minio-access-key";
    private final String secretKey = "minio-secret-key";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Manually instantiate MinioImageService with the required parameters
        minioImageService = new MinioImageService(endpoint, accessKey, secretKey);
        minioImageService = spy(minioImageService); // Spy to mock interactions if needed
    }

    @Test
    void testUploadImage_Exception() throws Exception {
        // Arrange
        when(multipartFile.getOriginalFilename()).thenReturn("test-image.jpg");
        when(multipartFile.getInputStream()).thenThrow(new RuntimeException("File upload error"));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            minioImageService.uploadImage(multipartFile);
        });
        assertTrue(exception.getMessage().contains("Fehler beim Hochladen des Bildes"));
    }
    @Test
    void testGetImage_Exception() throws Exception {
        // Arrange
        String fileName = "test-image.jpg";
        when(minioClient.getObject(any(GetObjectArgs.class))).thenThrow(new RuntimeException("Minio error"));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            minioImageService.getImage(fileName);
        });
        assertTrue(exception.getMessage().contains("Fehler beim Abrufen des Bildes"));
    }
}
