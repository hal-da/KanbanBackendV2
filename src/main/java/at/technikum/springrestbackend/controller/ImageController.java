package at.technikum.springrestbackend.controller;
import at.technikum.springrestbackend.dto.ImageUrlDto;
import at.technikum.springrestbackend.security.UserPrincipal;
import at.technikum.springrestbackend.service.MinioImageService;
import at.technikum.springrestbackend.service.UserService;
import lombok.RequiredArgsConstructor;
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

    private final MinioImageService minioImageService;
    private final UserService userService;

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
            @RequestParam("image") MultipartFile image,
            @AuthenticationPrincipal UserPrincipal userPrincipal) throws Exception {
        System.out.println("uploadImage");
        String imageURl = minioImageService.uploadImage(image);
        System.out.println("imageURl: " + imageURl);
        String userId = userPrincipal.getUserId();
        System.out.println("userId: " + userId);
        userService.addUserImageUrlToUser(imageURl, userId);
        System.out.println("image added to user");
        return new ImageUrlDto(imageURl);
    }

    @DeleteMapping("/{id}")
    public void deleteImage(
            @PathVariable String id,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        System.out.println("deleteImage " + id);

    }
}
