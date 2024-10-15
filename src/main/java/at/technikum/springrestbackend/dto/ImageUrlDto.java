package at.technikum.springrestbackend.dto;

public class ImageUrlDto {
    private String imageUrl;

    public ImageUrlDto(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
