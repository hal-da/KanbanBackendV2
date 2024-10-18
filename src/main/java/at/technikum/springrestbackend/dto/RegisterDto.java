package at.technikum.springrestbackend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDto {
    @NotBlank
    private String email;
    @NotBlank
    private String password1;
    @NotBlank
    private String password2;
    @NotBlank
    private String userName;
    @NotBlank
    private String cca3;

    public RegisterDto() {
    }

    public RegisterDto(String email, String password1, String password2, String userName, String cca3) {
        this.email = email;
        this.password1 = password1;
        this.password2 = password2;
        this.userName = userName;
        this.cca3 = cca3;
    }
}
