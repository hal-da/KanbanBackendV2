package at.technikum.springrestbackend.service;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.MinioException;
import io.minio.messages.Bucket;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@Service
public class MinioImageService {

    private final MinioClient minioClient;

    public MinioImageService(
            @Value("${minio.endpoint}") String endpoint,
            @Value("${minio.access-key}") String accessKey,
            @Value("${minio.secret-key}") String secretKey) {

        System.out.println("MinioImageService: " + endpoint + " " + accessKey + " " + secretKey + " ################");


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

    public void uploadFile(String bucketName, String objectName, MultipartFile file) throws Exception {
        try (InputStream inputStream = file.getInputStream()) {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .stream(inputStream, file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
        } catch (MinioException e) {
            throw new Exception("Error occurred while uploading file to MinIO: " + e.getMessage());
        }
    }
}
