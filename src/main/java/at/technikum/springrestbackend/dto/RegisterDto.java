package at.technikum.springrestbackend.dto;

import at.technikum.springrestbackend.exception.PasswordsNotSimilarException;
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

    public RegisterDto() {
    }

    public RegisterDto(String email, String password1, String password2, String userName) {

        System.out.println("RegisterDto: "
                + email + " " + password1 + " " + password2 + " " + userName);
        this.email = email;
        this.password1 = password1;
        this.password2 = password2;
        this.userName = userName;
        if (!password1.equals(password2)) {
            throw new PasswordsNotSimilarException("Passwords are not similar");
        }
    }
}
