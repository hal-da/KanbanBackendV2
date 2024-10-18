package at.technikum.springrestbackend.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ImageUrlDto {
    private String imageUrl;

    public ImageUrlDto(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
