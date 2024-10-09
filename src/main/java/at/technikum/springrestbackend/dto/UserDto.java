package at.technikum.springrestbackend.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDto extends BaseModelDto {
    private String id;
    private String email;
    private String role;
    private String userName;

    public UserDto(String id, String email, String role, String userName) {
        this.id = id;
        this.email = email;
        this.role = role;
        this.userName = userName;
    }

    public UserDto() {
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
