package at.technikum.springrestbackend.controller;
import at.technikum.springrestbackend.dto.ImageUrlDto;
import at.technikum.springrestbackend.service.MinioImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/images")
public class ImageController {

    private final MinioImageService minioImageService;


    @GetMapping("/{fileName}")
    public ResponseEntity<byte[]> getImage(@PathVariable String fileName) {
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(minioImageService.getImage(fileName));
    }


    @PostMapping
    public ImageUrlDto uploadImage(
            @RequestParam("image") MultipartFile image) throws Exception {
        return new ImageUrlDto(minioImageService.uploadImage(image));
    }
}
