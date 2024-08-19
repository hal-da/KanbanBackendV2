package at.technikum.springrestbackend.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PublicUserDto {
    private String id;
    private String username;

    public PublicUserDto(String id, String username) {
        this.id = id;
        this.username = username;
    }

    public PublicUserDto() {
    }

}
