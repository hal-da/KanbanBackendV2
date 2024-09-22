package at.technikum.springrestbackend.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PublicUserDto {
    private String id;
    private String userName;

    public PublicUserDto(String id, String userName) {
        this.id = id;
        this.userName = userName;
    }

    public PublicUserDto() {
    }

}
