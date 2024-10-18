package at.technikum.springrestbackend.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
@Setter
@Getter
public class PublicUserDto extends BaseModelDto {
    private String id;
    private String userName;
    private String role;
    private String imageUrl;
    private String cca3;

    public PublicUserDto(
            String id,
            String userName,
            String role,
            String imageUrl,
            Date createdAt,
            Date lastChangeAt,
            String cca3
    ) {
        System.out.println("PublicUserDto constructor, cca: " + cca3);
        this.id = id;
        this.userName = userName;
        this.role = role;
        this.imageUrl = imageUrl;
        super.setCreatedAt(createdAt);
        super.setLastChangeAt(lastChangeAt);
        this.cca3 = cca3;
    }
}
