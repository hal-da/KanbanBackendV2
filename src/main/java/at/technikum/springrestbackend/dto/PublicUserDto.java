package at.technikum.springrestbackend.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PublicUserDto {
    private String id;
    private String userName;
    private String role;

    public PublicUserDto(String id, String userName, String role) {
        this.id = id;
        this.userName = userName;
        this.role = role;
    }

    public PublicUserDto() {
    }

}
