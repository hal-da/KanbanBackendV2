package at.technikum.springrestbackend.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class UserDto extends BaseModelDto {
    private String id;
    private String email;
    private String role;
    private String userName;
    private String imageUrl;

    public UserDto(
            String id,
            String email,
            String role,
            String userName,
            String imageUrl,
            Date createdAt,
            Date lastChangeAt
    ) {
        this.id = id;
        this.email = email;
        this.role = role;
        this.userName = userName;
        this.imageUrl = imageUrl;
        super.setCreatedAt(createdAt);
        super.setLastChangeAt(lastChangeAt);
    }
}
