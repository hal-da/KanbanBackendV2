package at.technikum.springrestbackend.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class PublicUserDto extends BaseModelDto {
    private String id;
    private String userName;
    private String role;
    private String imageUrl;

    public PublicUserDto(
            String id,
            String userName,
            String role,
            String imageUrl,
            Date createdAt,
            Date lastChangeAt
    ) {
        this.id = id;
        this.userName = userName;
        this.role = role;
        this.imageUrl = imageUrl;
        super.setCreatedAt(createdAt);
        super.setLastChangeAt(lastChangeAt);
    }
}
