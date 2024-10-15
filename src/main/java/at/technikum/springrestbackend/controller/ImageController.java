package at.technikum.springrestbackend.controller;


import at.technikum.springrestbackend.security.UserPrincipal;
import at.technikum.springrestbackend.service.MinioImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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

    @GetMapping("/{id}")
    public String getImage(@PathVariable String id) {
        System.out.println("getImage " +  id);
        return "getImage";
    }


    @PostMapping
    public String uploadImage(
            @RequestParam("image") MultipartFile image,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        System.out.println("uploadImage");
        return "createImage";
    }

    @DeleteMapping("/{id}")
    public void deleteImage(
            @PathVariable String id,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        System.out.println("deleteImage " + id);

    }
}
