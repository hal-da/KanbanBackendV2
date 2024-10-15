package at.technikum.springrestbackend.service;
import io.minio.*;
import io.minio.errors.MinioException;
import io.minio.messages.Bucket;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@Service
public class MinioImageService {

    private final MinioClient minioClient;

    @Value("${minio.bucket-name}")
    private String bucketName;

    public MinioImageService(
            @Value("${minio.endpoint}") String endpoint,
            @Value("${minio.access-key}") String accessKey,
            @Value("${minio.secret-key}") String secretKey) {

        System.out.println(bucketName + " " + endpoint + " " + accessKey + " " + secretKey + " ################");
        this.minioClient = MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
        listBuckets();
    }

    public void listBuckets() {
        try {
            List<Bucket> bucketList = minioClient.listBuckets();
            System.out.println("Minio connection success, total buckets: " + bucketList.size());
        } catch (MinioException e) {
            System.out.println("Minio connection failed: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String uploadImage(MultipartFile file) throws Exception {
        try {
            System.out.println("image name: " + file.getOriginalFilename());
            String imageId = UUID.randomUUID().toString();
            String fileName = imageId + "-" + file.getOriginalFilename();
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );

            return fileName;
        } catch (Exception e) {
            throw new RuntimeException("Fehler beim Hochladen des Bildes", e);
        }
    }

    public byte[] getImage(String fileName) {
        try {
            try (InputStream stream = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .build())) {

                return stream.readAllBytes();
            }
        } catch (Exception e) {
            throw new RuntimeException("Fehler beim Abrufen des Bildes", e);
        }
    }

}
