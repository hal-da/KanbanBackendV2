package at.technikum.springrestbackend.controller;
import at.technikum.springrestbackend.dto.ImageUrlDto;
import at.technikum.springrestbackend.security.UserPrincipal;
import at.technikum.springrestbackend.service.MinioImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/images")
public class ImageController {

    @Value("${minio.bucket-name}")
    private String imageBucketName;
    private final MinioImageService minioImageService;

    @GetMapping
    public String getImages() {
        System.out.println("getImages");
        return "getImages";
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<byte[]> getImage(@PathVariable String fileName) {

        // Bild als Response zurücksenden
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(minioImageService.getImage(fileName));
    }


    @PostMapping
    public ImageUrlDto uploadImage(
            @RequestParam("image") MultipartFile image) throws Exception {
        System.out.println("uploadImage");
        String imageURl = minioImageService.uploadImage(image);
        System.out.println("imageURl: " + imageURl);
        return new ImageUrlDto(imageURl);
    }

    @DeleteMapping("/{id}")
    public void deleteImage(
            @PathVariable String id,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        System.out.println("deleteImage " + id);

    }
}
