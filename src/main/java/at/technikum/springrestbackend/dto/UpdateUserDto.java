package at.technikum.springrestbackend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UpdateUserDto {
    @NotBlank
    private String email;
    @NotBlank
    private String userName;
    @NotBlank
    private String imageUrl;
    @NotBlank
    private String cca3;
}
